import csv
import sys

'''
    Function for loading the raw data to be pre-processed.
'''
def get_csv_data(filename):
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        data = list(reader)
        f.close()
    return data

'''
    Function for saving the pre-processed data.
'''
def save_csv_data(filename, processed):
    with open(filename, 'w') as f:
        writer = csv.writer(f)
        writer.writerows(processed)
        f.close()

data = get_csv_data('ce889_dataCollection.csv')

print('raw data')
for row in data[:5]:
    print(row)
print('\n')

'''
    Removing rows that do not have values in exactly 4 columns.
'''
for row in data:
    if len(row) != 4:
        print('dropped row without 4 columns ' + row)
        data = data[:data.index(row)] + data[data.index(row)+1:]

print('removed rows with invalid columns')
for row in data[:5]:
    print(row)
print('\n')

'''
    Removing duplicate rows.
'''
new_data = []
for row in data:
    if not row in new_data:
        new_data.append(row)

print('removed duplicate rows')
for row in new_data[:5]:
    print(row)
print('\n')

'''
    Re-Scaling each value between the minimum value and the maximum value.
'''
maxint = sys.maxsize
minint = -sys.maxsize - 1
min = [maxint, maxint, maxint, maxint]
max = [minint, minint, minint, minint]
for row in new_data:
    for i in range(len(row)):
        if float(row[i]) > max[i]:
            max[i] = float(row[i])
        if float(row[i]) < min[i]:
            min[i] = float(row[i])

print(min[0], max[0], min[1], max[1])
print(min[2], max[2], min[3], max[3])

for row in new_data:
    for i in range(len(row)):
        row[i] = (float(row[i]) - min[i]) / (max[i] - min[i])

print('scaled (normalised) data between 0 and 1')
for row in new_data[:5]:
    print(row)
print('\n')

save_csv_data('ce889_dataCollection_preprocessed.csv', new_data)
