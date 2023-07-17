# Import python modules
import numpy as np
import kaggle
import numpy as np
from sklearn.linear_model import Lasso
from sklearn.linear_model import Ridge
import time


import matplotlib.pyplot as plt
from sklearn.neighbors import KNeighborsRegressor
from sklearn.model_selection import KFold


from sklearn.tree import DecisionTreeRegressor


# Read in train and test data
def read_dataA():
    print('Reading air foil dataset ...')
    train_data = np.load('../../Data/dataA/train.npy')
    train_x = train_data[:, 0:train_data.shape[1] - 1]
    train_y = train_data[:, train_data.shape[1] - 1]
    test_data = np.load('../../Data/dataA/test.npy')
    test_x = test_data

    return (train_x, train_y, test_x)


def read_dataB():
    print('Reading air quality dataset ...')
    train_data = np.load('../../Data/dataB/train.npy')
    train_x = train_data[:, 0:train_data.shape[1] - 1]
    train_y = train_data[:, train_data.shape[1] - 1]
    test_data = np.load('../../Data/dataB/test.npy')
    test_x = test_data

    return (train_x, train_y, test_x)


# Compute MAE
def compute_error(y_hat, y):
    # mean absolute error
    return np.abs(y_hat - y).mean()


# Simple method to make code shorter
def create_decision_trees(depth):
    regressor = DecisionTreeRegressor(criterion='mae', max_depth=depth)
    return regressor


# Function that takes kfold, inputs and a model and cross validates and spits out the error
def cross_validate(theFolds, inputx, inputy, model):
    error = 0
    for (train_ind, test_ind) in theFolds.split(inputx, inputy):  # split data along requested number of folds
        xtrain = inputx[train_ind]  # set variables to values at index within arrays
        xtest = inputx[test_ind]
        ytrain = inputy[train_ind]
        ytest = inputy[test_ind]
        model = model.fit(xtrain, ytrain)  # fit the model to the training data
        error += compute_error(model.predict(xtest), ytest)  # compute error given test data
    return error  # return a float error


# Run the first set of decision trees, and make predictions
def run_dec_trees_pt1(inputx, inputy, test, params):
    theFolds = KFold(n_splits=5)
    lowestErr = 999  # Lowest error starts at a value my code will not reach, so it will be replaced on first iteration
    bestTree = create_decision_trees(3)  # placeholder, variable declared outside of loop for use outside of loop
    time_array = []  # was used for making time graph, since removed to not clog output
    depth_array = []
    MAE_array = []
    for x in params:
        t0 = time.time()
        depth_array.append(x)  # used to make time graph and tables in report
        tree = create_decision_trees(x)
        error = cross_validate(theFolds, inputx, inputy, tree)  # CV method
        if error < lowestErr:  # check if each new model is better than prev best
            bestTree = tree
            lowestErr = error
        MAE_array.append(error)
        t1 = time.time()
        # print(t1-t0)  # Used matplotlib plt.plot to make the graph from the time_array and depth array
    print("DT1")
    print(bestTree)
    print(MAE_array)
    print(depth_array)
    return bestTree.predict(test)


# Run the second set of decision trees, and make predictions
# basically the same method with different hardcoded values
# Did not produce promising results for the kaggle testing
def run_dec_trees_pt2(inputx, inputy, test):
    theFolds = KFold(n_splits=5)
    lowestErr = 999
    bestTree = create_decision_trees(20)
    time_array = []
    depth_array = []
    MAE_array = []
    for x in range(1, 6):
        t0 = time.time()
        tree = create_decision_trees(15 + (x * 5))  # here's the different part
        depth_array.append(15 + (x * 5))
        error = cross_validate(theFolds, inputx, inputy, tree)
        if error < lowestErr:
            bestTree = tree
            lowestErr = error
    MAE_array.append(error)
    print("DT")
    print(bestTree)
    print(MAE_array)
    print(depth_array)
    return bestTree.predict(test)


# K nearest neighbors
def run_knn_pt1(inputx, inputy, test, params, splits):  # takes input params and splits for kaggle testing
    theFolds = KFold(n_splits=splits)
    lowestErr = 999
    bestKnn = KNeighborsRegressor(n_neighbors=3)  # placeholder
    MAE_array = []  # data storage
    params_array = []

    for x in params:  # iterate over each thing in the input params array
        params_array.append(x)
        knn = KNeighborsRegressor(n_neighbors=x)
        error = cross_validate(theFolds, inputx, inputy, knn)  # CV using my above method
        MAE_array.append(error / splits)  # calculate error
        if error < lowestErr:  # check if each new model is better than prev best
            bestKnn = knn
            lowestErr = error
    print("KNN")
    print(bestKnn)
    print(MAE_array)
    print(params_array)
    return bestKnn.predict(test)


def linear_model(inputx, inputy, test, params):  # since the question requires both to be compared together
    lowestErr = 999
    theFolds = KFold(n_splits=5)  # Only 5 folds required so its hardcoded in
    bestModel = Ridge(alpha=params[0])  # placeholder
    MAE_array_rid = []  # Data storage so I can input it into report
    MAE_array_lass = []
    params_array = []
    for x in params:  # iterate over each inputted hyper parameter
        rid = Ridge(alpha=x)
        lass = Lasso(alpha=x)
        params_array.append(x)
        errorRid = cross_validate(theFolds, inputx, inputy, rid)  # calculate the error using my CV method
        errorLass = cross_validate(theFolds, inputx, inputy, lass)
        MAE_array_rid.append(errorRid)  # data storage
        MAE_array_lass.append(errorLass)
        if errorRid < errorLass:  # if ridge is better, we compare that to prev best
            error = errorRid
            if error < lowestErr:
                bestModel = rid  # store the best model
                lowestErr = error  # and the lowest error for comparrison
        else:  # else we compare lass to prev best
            error = errorLass
            if error < lowestErr:
                bestModel = lass
                lowestErr = error

    print("Linear")  # just printing so I can copy paste it into report
    print(bestModel)
    print("Ridge")
    print(MAE_array_rid)
    print("Lasso")
    print(MAE_array_lass)
    print(params_array)
    return bestModel.predict(test)  # make predictions


def kaggle_stuff():  # KNN with leave 2 out kfold
    sizeof = int(1342 / 2)
    return run_knn_pt1(train_x, train_y, test_x, [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19],
                       sizeof)


def kaggle_stuff_b():  # KNN with 10 folds, for runtime purposes
    return run_knn_pt1(train_xb, train_yb, test_xb, [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19],
                       10)


############################################################################


train_x, train_y, test_x = read_dataA()
print('Train=', train_x.shape)
print('Test=', test_x.shape)

train_xb, train_yb, test_xb = read_dataB()
print('Train=', train_xb.shape)
print('Test=', test_xb.shape)

# Create dummy test output values
predicted_y = np.ones(test_x.shape[0]) * -1
# Output file location
file_name = '../Predictions/dataA/best.csv'
# Writing output in Kaggle format
print('Writing output to ', file_name)
kaggle.kaggleize(predicted_y, file_name)

# print(run_dec_trees_pt1(train_x,train_y,test_x))
# predicted_y = run_dec_trees_pt1(train_x,train_y,test_x)
# file_name = '../Predictions/dataA/DTA.csv'
# kaggle.kaggleize(predicted_y, file_name)

# predicted_y = run_dec_trees_pt2(train_xb,train_yb,test_xb)
# file_name = '../Predictions/dataB/DTB.csv'
# kaggle.kaggleize(predicted_y, file_name)

# predicted_y = run_knn_pt1(train_x,train_y,test_x,[3,5,10,20,25])
# file_name = '../Predictions/dataA/KNNA.csv'
# kaggle.kaggleize(predicted_y, file_name)

# predicted_y = run_knn_pt1(train_xb, train_yb, test_xb,[3,5,10,20,25])
# file_name = '../Predictions/dataB/KNNB.csv'
# kaggle.kaggleize(predicted_y, file_name)

# predicted_y = linear_model(train_x, train_y, test_x, [10**-6, 10**-4, 10**-2, 1, 10])
# file_name = '../Predictions/dataA/LinearA.csv'
# kaggle.kaggleize(predicted_y, file_name)

# predicted_y = linear_model(train_xb, train_yb, test_xb, [10**-4, 10**-2, 1, 10])
# file_name = '../Predictions/dataB/LinearB.csv'
# kaggle.kaggleize(predicted_y, file_name)


predicted_y = kaggle_stuff()
file_name = '../Predictions/dataA/best.csv'
kaggle.kaggleize(predicted_y, file_name)

predicted_y = kaggle_stuff_b()
file_name = '../Predictions/dataB/best.csv'
kaggle.kaggleize(predicted_y, file_name)
