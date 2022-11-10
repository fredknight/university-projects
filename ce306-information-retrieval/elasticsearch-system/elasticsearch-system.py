from elasticsearch import helpers, Elasticsearch
import collections
import csv

#function to update/apply analyzer/filters/tfidf to the index
def update_settings(es, index_name):
    es.indices.close(index=index_name) #index must be closed to update settings
    es.indices.put_settings(index=index_name, body={
        "settings": {
            "analysis": {
                "analyzer": { #defining a custom analyzer
                    "custom_analyzer": {
                        "type": "custom",
                        "tokenizer": "standard",
                        "filter": [ #selecting the filters the analyzer will use
                            "lowercase",
                            "english_stop",
                            "english_stemmer"
                        ]
                    }
                },
                "filter": { #defining custom filters
                    "english_stemmer": {
                        "type": "kstem",
                    },
                    "english_stop": {
                        "type": "stop",
                        "stopwords": "_english_" #specifying to use english stop words
                    }
                }
            },
            "similarity": { #defining a custom scoring/ranking model
                "scripted_tfidf": {
                    "type": "scripted", #scripted means that the equation is coded
                    "script": {
                        "source": "double tf = doc.freq; double idf = Math.log(field.docCount/term.docFreq); return query.boost * tf * idf;" #tfidf equation
                    }

                }
            }
        }
    })
    es.indices.open(index=index_name) #opening the index so data can be indexed


#function to map the analyzer and scoring model to each field
def update_mapping(es, index_name, doctype):
    es.indices.put_mapping(index=index_name, doc_type=doctype, body={
        "properties": {
            "Release Year": {
                "type": "text",
                "analyzer": "custom_analyzer", #assigning the custom analyzer to the field
                "similarity": "scripted_tfidf" #assigning the custom scoring model to the field
            },
            "Title": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            },
            "Origin/Ethnicity": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            },
            "Director": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            },
            "Cast": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            },
            "Genre": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            },
            "Wiki Page": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            },
            "Plot": {
                "type": "text",
                "analyzer": "custom_analyzer",
                "similarity": "scripted_tfidf"
            }
        }
    })


#function to (re)index the data
def index_data():
    try:
        with open("wiki_movie_plots_deduped.csv") as file:
            index_name = 'movie_records'
            doctype = 'movie_record'
            reader = csv.reader(file) #read contents of .csv file
            headers = []
            index = 0
            es.indices.delete(index=index_name, ignore=[400, 404])  #if an index exists with the index_name, delete it
            es.indices.create(index=index_name, body={
                'settings': {
                    'index': {
                        'number_of_shards': 1 #set number of shards to 1 so search results are replicable
                    }
                }
            }, ignore=400)  # create the index
            update_settings(es, index_name)
            update_mapping(es, index_name, doctype)

            #index the content of the .csv file row-by-row (document-by-document)
            for row in reader:
                if (index <= 1000):  #selecting the maximum amount of rows/documents to index
                    try:
                        if (index == 0):
                            headers = row  #first row indexed are the field headers
                        else:
                            entries = {}
                            for i, content in enumerate(row):
                                entries[headers[i]] = content #storing the .csv file contents in a dictionary for that row

                            es.index(index=index_name, doc_type=doctype, body=entries)  #add row to index

                    except Exception as e:
                        print('error: ' + str(e) + ' in' + str(index))
                    index += 1 #count number of rows/documents indexed (not including headers)

                else:
                    break

        print("Finished Indexing " + str(index - 1) + " Entries") #return amount of indexed rows/documents
        file.close()

    except Exception as e:
        print("error: " + str(e))


#function to search the index by subsituting user inputs into pre-built queries
def search_index(field, user_input):
    res = es.search(index="movie_records", doc_type="movie_record", from_=0, size=3, body={ #retrieve no more than 3 documents
        "query": {"match": {field: user_input}}}) #query the index using the field and search term selected by the user

    print("\n%d documents found" % res['hits']['total']) #returns the total amount of documents found in the index
    for doc in res['hits']['hits']: #for the 3 documents selected, print all field data
        print("\n")
        print("Release Year: %s" % (doc['_source']['Release Year']))
        print("Title: %s" % (doc['_source']['Title']))
        print("Origin/Ethnicity: %s" % (doc['_source']['Origin/Ethnicity']))
        print("Director: %s" % (doc['_source']['Director']))
        print("Cast: %s" % (doc['_source']['Cast']))
        print("Genre: %s" % (doc['_source']['Genre']))
        print("Wiki Page: %s" % (doc['_source']['Wiki Page']))
        print("Plot: %s" % (doc['_source']['Plot']))
        print("Score: %s" % (doc['_score'])) #return the score of the document in relation to the search term's relevance
        print("\n")


#simple function that prints the field headers to the user
def print_headers():
    print("\nFields:")
    print("----------------")
    print("Release Year")
    print("Title")
    print("Origin/Ethnicity")
    print("Director")
    print("Cast")
    print("Genre")
    print("Wiki Page")
    print("Plot\n")


#function to act as the CLI for the script
def cmd_program():
    #loop function infinitely
    while True:
        print_headers()
        field = input("Enter field to search, or exit by typing '//exit': \n")
        if field.lower() == "//exit":
            break; #breaks the loop, ending the script
        user_input = input("Enter keyword to search, or return to field selection by typing '//return': \n")
        if user_input.lower() == "//return":
            continue; #iteratess the while loop, starting back at the top of the function
        search_index(field, user_input)


if __name__ == "__main__":
    es = Elasticsearch()  #open elasticsearch client
    print("connecting to elasticsearch local server...")

    #continuously ping the Elasticsearch search engine until successful
    while not es.ping():
        continue

    #alert the user if a connection is successful
    if es.ping():
        print("established connection to elasticsearch local server")

    print("CE306 Movie Plots")
    if es.indices.exists(index="movie_records"): #checks if an index exists
        print("Index already exists...")
        confirm = input("Overwrite index (Y/N)?: ")
        if confirm.lower() == "y": #reindex data
            print("Overwriting index.")
            index_data()
        elif confirm.lower() == "n": #use existing index
            print("Using existing index.")
        else:
            print("Invalid option: Exiting program...")
            quit() #ends script
    else:
        index_data()

    #runs the CLI
    #print_headers() and search_index() functions are called within this function,
    #so this can be commented out to purely use the script to (re)index data
    cmd_program()
