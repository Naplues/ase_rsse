Rebuild of ApiRec 
======
(Nguyen et al., 2016, https://dl.acm.org/citation.cfm?doid=2950290.2950333)

How to use the recommender
------------

1) Run the ase.rsse.apirec.transactions.TransactionCreator to create the data set the recommender operates on (i.e. Transactions in ApiRecs terminology)

2) Use the predictKBest method of ase.rsse.apirec.recommender.ApiRecRecommender to predict the k-best method calls given two consecutive code completion events. Plese refer to demo.RecommenderDemo for a detailed description on how to inst	antiate and call the recommender.

Overview
------------

Note: Important classes contain comments that explain the most fundamental operations.

* ase.rsse.apirec.recommender
Contains the classes that represent the recommender and it's interface. Is designed to be as small as possible to be easily understandable. It uses the data structures and utilities of the other packages.

* ase.rsse.apirec.transactions
This package contains everything necessary to create and use transactions. Of special interest are:
	* TransactionCreator -> used to create the data set the recommender operates on
	* TransactionUtility -> contains the logic on how to create transactions based on code completion events
* ase.rsse.apirec.transactions.changecontext
Data structures necessary to represent the change context.
* ase.rsse.apirec.transactions.codecontext
Data structures necessary to represent the code context.
* ase.rsse.apirec.transactions.query
Data structures necessary to represent the query.
* ase.rsse.utilities
Contains all utilities. The utilities implement most of the functionality and rely on the data structures mentioned before. 
	* IoUtility -> used to serialize and deserialize all the relevant data
	* JsonUtility -> used to convert Java objects into JSON-Strings and vice versa
	* ScoringUtility.java -> used to score candidate changes. This is the most important class in terms of recommender functionality. It attempts to reimplement scoring as described by Nguyen et al. (2016). Due to the fact that there is no description on how to calculate the weight of data dependency and the weight of scope as well as the distance in the paper of Nguyen et al. and the fact the KaVE data set does not provide the data necessary (getChildren of ISSTNodes always returns empty ArrayLists) we had to remove the parts that uses this information in a later stage of the project. This is why we ignore this information completely.
* ase.rsse.app
	Contains all tests. Ensures that the core functionality necessary for the recommender work.