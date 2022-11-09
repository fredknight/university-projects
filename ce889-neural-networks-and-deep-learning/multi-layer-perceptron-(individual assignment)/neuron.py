from math import exp
from random import uniform

class Neuron:
    '''
        __init__():
            The constructor to initialize the attributes of the specific 'Neuron' class object:
            -   'activation_value' contains the activation value (output) of that node.
            -   'weights' contains a list of weights between that neuron and all nodes 
                in the next layer (besides the bias). These are initialized as a random 
                value between -1 and 1.
            -   'gradient' contains the calculated gradient for those neurons within the 
                output layer and the hidden layer.
            -   'temporary_error' contains a value from the error calculation that is then
                used to calculate the gradient (this avoids some repeaated code).
            -   'delta_weights' contains a list of the delta weights calculated during
                the weight update step. These are initialized as 0, so the first run will
                not use any delta weights.
    '''
    def __init__(self, activation_value, number_of_weights):
        self.activation_value = activation_value
        self.weights = []
        self.gradient = 0
        self.temporary_error = 0
        self.delta_weights = []

        [self.weights.append(uniform(-1, 1)) for i in range(number_of_weights)]
        [self.delta_weights.append(0) for i in range(number_of_weights)]

    '''
        __repr__():
            The string representation of the neuron that will be output when the neuron is printed:
            -   Allows for easier display of each neuron's activation value and weight list.
    '''
    def __repr__(self):
        return '-> Neuron: activation_value = ' + str(self.activation_value) + ', weights = ' + str(self.weights)

    '''
        activation_function():
            Sigmoid activation function to transform the input from the previous layer to an output.
    '''
    def activation_function(self, LAMBDA, x):
        return 1 / (1 + exp(-LAMBDA * x))

    '''
        activation_function_derivative():
            Returns the derivative of the sigmoid activation function to calculate the slope of the 
            neuron output.
    '''
    def activation_function_derivative(self, LAMBDA, x):
        return self.activation_function(LAMBDA, x) * (1 - self.activation_function(LAMBDA, x))
    
    '''
        weight_multiplication():
            Multiplies the neuron's current activation value by the weight between it and another neuron, 
            so the weighted sum of the input can be calculated.
    '''
    def weight_multiplication(self, other_index):
        return self.activation_value * self.weights[other_index]