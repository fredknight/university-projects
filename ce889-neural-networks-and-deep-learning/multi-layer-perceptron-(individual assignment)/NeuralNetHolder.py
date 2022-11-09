from neuron import Neuron

class NeuralNetHolder:

    def __init__(self):
        super().__init__()

        '''
            Initialising the neural network in the same was as in the neural network class.
        '''

        self.LAMBDA = 0.6
        self.ALPHA = 0.05

        input_neurons = 2
        hidden_neurons = 4
        output_neurons = 2

        self.neural_network = []

        input_layer = []
        hidden_layer = []
        output_layer = []

        input_layer_bias = Neuron(1, hidden_neurons)
        input_layer.append(input_layer_bias)
        hidden_layer_bias = Neuron(1, output_neurons)
        hidden_layer.append(hidden_layer_bias)

        [input_layer.append(Neuron(0, hidden_neurons)) for i in range(input_neurons)]
        [hidden_layer.append(Neuron(0, output_neurons)) for i in range(hidden_neurons)]
        [output_layer.append(Neuron(0, 0)) for i in range(output_neurons)]

        self.neural_network.extend([input_layer, hidden_layer, output_layer])

        '''
            Iteratively assigning the saved weights from the final trained model to each neuron in 
            the network. These need to be replaced each time the neural network is trained.
        '''

        input_weights = [[0.9431666491659073,0.31862402334691686,-1.373431820530664,-1.3525142872585736],
                         [-7.019337191365026,-4.078538118474283,6.233376325824882,3.152839497480561],
                         [-3.655684365620413,4.152157623182492,3.551448395844868,-2.637516416361787]]

        hidden_weights = [[1.5593119676573168,-1.6143348920466596],
                          [2.958773929369045,-0.004900933854394933],
                          [2.018078532126166,-3.8467003854725084],
                          [-2.9757825183265147,-1.9661318827070522],
                          [-1.7826207082872052,3.6359657246807884]]

        for i, layer in enumerate(self.neural_network[:-1]):
            if i == 0:
                for n, neuron in enumerate(layer):
                    neuron.weights = input_weights[n]
            else:
                for n, neuron in enumerate(layer):
                    neuron.weights = hidden_weights[n]
    
    def predict(self, input_row):
        # WRITE CODE TO PROCESS INPUT ROW AND PREDICT X_Velocity and Y_Velocity
        #pass # this pass can be removed once you add some code
        
        '''
            Splitting the string into a list of inputs.
        '''

        print(input_row)
        input_row = input_row.split(',')
        print(input_row[0], input_row[1])

        '''
            Normalizing the list of inputs using the min and max values from the pre-processing.
            The activation values of the input layer neurons are then assigned these values.
        '''

        min = [-555.4067055701046, 65.21294728091152, -4.028467508330568, -6.863960204169425]
        max = [562.8829484924756, 494.6800898562841, 7.978024199581206, 6.6446371866878575]

        input_row[0] = (float(input_row[0]) - min[0]) / (max[0] - min[0])
        input_row[1] = (float(input_row[1]) - min[1]) / (max[1] - min[1])

        self.neural_network[0][1].activation_value = float(input_row[0])
        self.neural_network[0][2].activation_value = float(input_row[1])

        '''
            Using the same feed-forward algorithm as within the neural network class.
        '''

        for current_layer, next_layer in zip(self.neural_network, self.neural_network[1:]):
            if next_layer == self.neural_network[-1]:
                for j, next_layer_neuron in enumerate(next_layer):
                    activation_value = 0
                    for current_layer_neuron in current_layer:
                        activation_value += current_layer_neuron.weight_multiplication(
                            j)
                    next_layer_neuron.activation_value = next_layer_neuron.activation_function(
                        self.LAMBDA, activation_value)

            else:
                for j, next_layer_neuron in enumerate(next_layer[1:]):
                    activation_value = 0
                    for current_layer_neuron in current_layer:
                        activation_value += current_layer_neuron.weight_multiplication(
                            j - 1)
                    next_layer_neuron.activation_value = next_layer_neuron.activation_function(
                        self.LAMBDA, activation_value)

        '''
            De-Normalizing the outputs from the algorithm
        '''

        X_Velocity = self.neural_network[-1][0].activation_value
        Y_Velocity = self.neural_network[-1][1].activation_value

        X_Velocity = X_Velocity*(max[2]-min[2]) + min[2]
        Y_Velocity = Y_Velocity*(max[3]-min[3]) + min[3]

        return X_Velocity, Y_Velocity