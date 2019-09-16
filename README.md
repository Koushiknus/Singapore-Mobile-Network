# Singapore-Mobile-Network-Usage


**[Programming Language]** - Kotlin

**[Architecture Used]** - Model - View - ViewModel 

**[Architecture Components Used**] - Mutable Live Data and Data Binding Library

**[Testing Framework]** - JUnit 4 

**[Network Layer Used]** - Retrofit 


**[Architecture Implementation Details]** - 
- Activity (User Interaction layer) made completely free from doing business logic .
- View Model takes care of processing all business logic . It gets the data from Repository 
- Repository takes care of getting data from API . Once it gets data it passes to view model 
- Once View Model receives data ,it process the data and return back to activity through Mutable Live data .
- As soon as new data available , activity observers are invoked and displays the data to user 
- View Model underwent unit testing as it performs all business logic.[JUnit4]
