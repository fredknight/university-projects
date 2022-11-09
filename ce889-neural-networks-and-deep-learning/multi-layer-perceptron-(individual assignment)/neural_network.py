from neuron import Neuron
import csv

class NeuralNetwork:
    '''
        Defining constants to be used. As we should not be changing the amount of input
        and output layer neurons, these should be defined as 2 and should not change.
        -   'LAMBDA' is the regularization rate of the neural network; used to better generalize
            unseen data.
        -   'ALPHA' is the momentum rate of the neural network; used to help try and prevent the 
            network becoming stuck in a local minimum whilst training.
        -   'neural_network' is the list of lists (list of layers) that forms the neural network.
        -   'data' is a list of rows of data that is read from a csv file.

    '''
    INPUT_NEURONS = 2
    OUTPUT_NEURONS = 2

    def __init__(self, hidden_neurons, LAMBDA, ALPHA):
        self.LAMBDA = LAMBDA
        self.ALPHA = ALPHA

        self.neural_network = []
        self.data = []

        input_layer = []
        hidden_layer = []
        output_layer = []

        '''
            Adding the bias neurons first, so they are known to be index '0' and can be 
            skipped if necessary. This also ensures no weights are generated between these 
            neurons and the previous layer.
        '''
        input_layer_bias = Neuron(1, hidden_neurons)
        input_layer.append(input_layer_bias)
        hidden_layer_bias = Neuron(1, self.OUTPUT_NEURONS)
        hidden_layer.append(hidden_layer_bias)

        [input_layer.append(Neuron(0, hidden_neurons)) for i in range(self.INPUT_NEURONS)]
        [hidden_layer.append(Neuron(0, self.OUTPUT_NEURONS)) for i in range(hidden_neurons)]
        [output_layer.append(Neuron(0, 0)) for i in range(self.OUTPUT_NEURONS)]

        self.neural_network.extend([input_layer, hidden_layer, output_layer])

    '''
        print():
            Allows me to view the structure of the neural network at any time:
            -   Used to validate network functions by calculating each step by 
                hand and comparing with the program's output.
    '''
    def print(self):
        print('\nInput Layer:\n' + '\n'.join(map(str, self.neural_network[0])))
        print('\nHidden Layer:\n' + '\n'.join(map(str, self.neural_network[1])))
        print('\nOutput Layer:\n' + '\n'.join(map(str, self.neural_network[2])))


    '''
        read_csv()
            Reads the pre-processed data from the csv file and stores it in 'self.data'.
    '''
    def read_csv(self, filename):
        try:
            with open(filename, 'r') as data:
                reader = csv.reader(data)
                self.data = list(reader)
                data.close()

            for row in self.data:
                for cell in range(len(row)):
                    row[cell] = float(row[cell])
            
            print('File Read Successful\n')

        except IOError:
            print('Unable to Open File:', filename)
            exit()

    '''
        save_weights():
            Saves the weights from the final model into a csv file, where they can be easily copied
            to the 'NeuralNetHolder.py' script.
    '''
    def save_weights(self, out):
        try:
            with open("weights.csv", "w") as f:
                wr = csv.writer(f)
                wr.writerows(out)
                f.close()

            print('\nWeights File Write Successful')

        except IOError:
            print('\nUnable to Write to File')
            exit()


    '''
        save_rmse():
            Saves the training and validation rmse values for each epoch to a csv file, so graphs
            can then be created.
    '''
    def save_rmse(self, out):
        try:
            with open("rmse.csv", "w") as f:
                wr = csv.writer(f)
                wr.writerows(out)
                f.close()

            print('RMSE File Write Successful')

        except IOError:
            print('Unable to Write to File')
            exit()
    
    '''
        save_test():
            Saves the expected values, predicted values, and the percentage error for each row in the 
            test portion of the data.
    '''
    def save_test(self, out):
        try:
            with open('test.txt', 'w') as f:
                for line in out:
                    f.writelines(line)
                    f.write('\n\n')
                f.close()

            print('Test Results File Write Successful')

        except IOError:
            print('Unable to Write to File')
            exit()

    '''
        feed_forward():
            Updates the outputs of each neuron by performing feed forward calculations:
            -   The input layer neurons' activation values (outputs) are set to that of the data inputs.
            -   iterating through 2 layers at a time, we calculate the activation values of the neurons
                in the next layer.
            -   If the next layer is an output layer, proceed as normal. However, if next layer is a 
                hidden layer we must skip the first neuron in that layer as that represents a bias.
    '''
    def feed_forward(self, inputs):
        for i in range(len(inputs)):
            self.neural_network[0][i + 1].activation_value = inputs[i]

        for current_layer, next_layer in zip(self.neural_network, self.neural_network[1:]):
            if next_layer == self.neural_network[-1]:
                for j, next_layer_neuron in enumerate(next_layer):
                    activation_value = 0
                    for current_layer_neuron in current_layer:
                        activation_value += current_layer_neuron.weight_multiplication(j)
                    next_layer_neuron.activation_value = next_layer_neuron.activation_function(self.LAMBDA, activation_value)

            else:
                for j, next_layer_neuron in enumerate(next_layer[1:]):
                    activation_value = 0
                    for current_layer_neuron in current_layer:
                        activation_value += current_layer_neuron.weight_multiplication(j - 1)
                    next_layer_neuron.activation_value = next_layer_neuron.activation_function(self.LAMBDA, activation_value)

    '''
        backwards_propagate():
            Calculates the gradient of each neuron to be used in the weight update function:
            -   Calls 'error_calculation()' to calculate the 'temporary error' of the neuron depending
                on what layer it's in. Each of these errors share the common part of the gradient
                equation below, so coding it twice is not necessary.
    '''
    def backwards_propagate(self, expected):
        for i, layer in enumerate(reversed(self.neural_network)):
            self.error_calculation(expected, layer, i)
            for neuron in layer:
                neuron.gradient = self.LAMBDA * neuron.activation_function_derivative(self.LAMBDA, neuron.activation_value)\
                                              * neuron.temporary_error

    '''
        error_calculation():
            Contains the part of the gradient calculation that is unique to the output layer or hidden layer.
            -   Removes the need to have the full gradient calculation be re-written.
            -   Calculated value is stored as the neuron's 'temporary_error' value.
            -   Skips the input layer as input layer neurons should not have an error gradient.
    '''
    def error_calculation(self, expected, current_layer, current_layer_index):
        if current_layer == self.neural_network[-1]:
            for i, neuron in enumerate(current_layer):
                neuron.temporary_error = expected[i] - neuron.activation_value
        elif current_layer != self.neural_network[0]:
            next_layer = self.neural_network[current_layer_index + 1]
            for neuron in current_layer:
                temporary_error = 0
                for i in range(len(next_layer)):
                    temporary_error += neuron.weights[i] * next_layer[i].gradient
                neuron.temporary_error = temporary_error

    '''
        weight_update()
            Updates the weights between neurons, using the gradient calculated in the above functions.
            -   Iterates through current and next layer, starting from the input layer and stopping at
                the 2nd to last layer.
            -   Checks to see if the current layer the input layer. If it is, then the next layer contains  
                a bias, so the first neuron must be skipped.
            -   Calculates the delta weight between the neurons by multiplying the gradient of the next neuron
                and the activation value of the current neuron with the learning rate.
            -   Momentum is applied by multiplying the momentum rate with the last calculated delta weight, 
                before this is added to the new delta weight.
            -   The weight between the 2 neurons is then updated, by summing the old weight with the calculated 
                delta weight.
    '''
    def weight_update(self, eta):
        for i in range(len(self.neural_network[:-1])):
            current_layer = self.neural_network[i]
            next_layer = self.neural_network[i + 1]
            for neuron in current_layer:
                for j in range(len(next_layer)):
                    if i == 0:
                        if j == 0:
                            continue
                        neuron.delta_weights[j-1] = (eta * next_layer[j].gradient * neuron.activation_value) + (self.ALPHA * neuron.delta_weights[j-1])
                        neuron.weights[j-1] += neuron.delta_weights[j-1]
                    else:
                        neuron.delta_weights[j] = (eta * next_layer[j].gradient * neuron.activation_value) + (self.ALPHA * neuron.delta_weights[j])
                        neuron.weights[j] += neuron.delta_weights[j]

    '''
        train():
            Splits the dataset into a training set and a validation set. Each step of the neural network 
            algorithm is performed in order, and then the RMSE is calculated. The validation set loop is the 
            as the training set, however only performs feed-forward using the calculated weights for that epoch:
            -   'split' is the point at which training data is spliced from.
            -   'split2' halfs the remaining value of the validation set to use for testing.
            -   'train' and 'validation' contain the training and validation sets.
            -   There are 2 types of 'sse', 'mse', and 'rmse'; for trian and validation.
            -   After the network has trained, the weights and rmse values are saved to separate csv files.
    '''
    def train(self, eta, epochs):
        self.read_csv('ce889_dataCollection_preprocessed.csv')
        split = int(len(self.data)*0.7)
        train = self.data[:split]
        split2 = int(len(self.data)*0.85)
        validation = self.data[split:split2]
        out_rmse = []
        out_rmse.append(['epoch', 'train_rmse', 'val_rmse'])
        for epoch in range(epochs):

            t_rmse = 0
            for row in train:
                inputs = row[:2]
                expected = row[2:]

                outputs = []
                self.feed_forward(inputs)
                [outputs.append(neuron.activation_value) for neuron in self.neural_network[2]]
                
                t_sse = 0
                for i in range(2):
                    t_sse += (expected[i] - outputs[i])**2

                t_mse = t_sse / len(train)
                t_rmse += t_mse**(1 / 2)

                self.backwards_propagate(expected)
                self.weight_update(eta)
            t_rmse = t_rmse/len(train)

            v_rmse = 0
            for row in validation:
                inputs = row[:2]
                expected = row[2:]

                outputs = []
                self.feed_forward(inputs)
                [outputs.append(neuron.activation_value) for neuron in self.neural_network[2]]
                
                v_sse = 0
                for i in range(2):
                    v_sse += (expected[i] - outputs[i])**2

                v_mse = v_sse / len(validation)
                v_rmse += v_mse**(1 / 2)
            v_rmse = v_rmse/len(validation)

            out_rmse.append([epoch + 1, t_rmse, v_rmse])

            print('[Epoch:', epoch + 1, '| Learning Rate:', eta,
                  '| Train RMSE: ', t_rmse, '| Validation RMSE: ', v_rmse, ']')

        self.print()
        out = []
        for layer in self.neural_network:
            for neuron in layer:
                out.append(neuron.weights)
        self.save_weights(out)
        self.save_rmse(out_rmse)

    '''
        test():
            Used to test the final weights using previously unseen data; as parameters were tuned using the 
            validation RMSE. This acts in the same way as 'train()', however calculates the average percentage 
            error based on how close the network was to the actual value:
            -   Test log data is saved to a text file.
    '''
    def test(self):
        split = int(len(self.data)*0.85)
        test = self.data[split:]
        out = []
        for row in test:
            inputs = row[:2]
            expected = row[2:]

            outputs = []
            self.feed_forward(inputs)
            [outputs.append(neuron.activation_value) for neuron in self.neural_network[2]]
            
            percentage_error = 0
            for i in range(len(expected)):
                percentage_error += ((expected[i] - outputs[i]) / expected[i]) * 100
            percentage_error = percentage_error/2

            line = '[Expected:', str(expected), '| Predicted:', str(outputs),']\n[ Average Percentage Error (%): ', str(percentage_error), ']'
            out.append(line)
        self.save_test(out)


'''
    Creating & Running the Network
'''
neural_network = NeuralNetwork(4, 0.6, 0.05)
neural_network.train(0.01, 32)
neural_network.test()