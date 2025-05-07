# LingoPal

## What is LingoPal?

**LingoPal** isn't like any old dictionary, it's your new language learning assistant.

**LingoPal** functions as a personal dictionary, allowing its users to create and update an accessible and individualized database of words that they come across while learning their new language of choice. Users will be able to add a new word to their dictionary, provide its translation to their native language, specify the word's grammatical category, and create an example sentence. Additonally, from either their entire personalized collection of words or a small subset of terms, users will be able to further refresh and practice their new vocabulary with quizzes.

**LingoPal** is designed to aid **anyone** on a language learning journey, from beginner students to avid polygots. So whether you're just trying to learn some phrases for your next trip abroad, keeping up with a language you learned in school, or working on your 5th new language, LingoPal is here to help you along the way.

## Why LingoPal?

In additon to computer science, one of my personal fascinations and hobbies is language learning. While I can't call myself fluent in anything other than English, I find the process of language acquistion so fun, rewarding, and quite unique to every person. I've had the opportunity of learning 3 other languages in academic settings *(and a few more with less commitment)*, and I came up with this idea for my Personal Project as a tool for those with a mutual interest in foreign language. 

I've always thought dictionaries were helpful, but sometimes physical copies can be impractical, and online ones lack the nuance and social implications that come from learning a language surrounded by its native speakers. So, this project is my way of closing that gap, by making a dictionary that allows its users to define words in a way that makes sense to them and their learning style to hopefully optimize language learning for everyone. 

## User Stories
- As a user, I want to be able to add a new term to my dictionary and specify its spelling, translation, grammatical category, and give an example sentence

- As a user, I want to be able to delete a term from my dictionary

- As a user, I want to be able to view all of the information for a term, like its translation, example sentence, and any of my notes on the word

- As a user, I want to be able to search for a term in my dictionary

- As a user, I want to be able to view a list of terms in my dictionary

- As a user, I want to be able to favourite a term from my dictionary

- As a user, I want to be able to view a list of favourite terms in my dictionary

- As a user, I want to be able to save my dictionary to file if I want

- As a user, when I start the application, I want to be given the option to load my dictionary from file

# Instructions for End User
- You can view all terms in your Dictionary in the Home Tab - after loading or creating a new dictionary. If you are loading a saved dictionary, all previous terms display immediately (given that the saved dictionary had at least one term), and if you are creating a new Dictionary, you must add a new Term first (as described below).

- You can add a Term to your Dictionary by going to the Add Tab and entering values for your new term into each of the fields (the first four fields are required to have entries) and pressing submit. By returning to the Home Tab and clicking the "View All Terms" button, the view pane of all terms will be updated to show the newly added term.

- You can generate the first required action related to the user story "view a list of favourite terms" by clicking the "View Favourite Terms" button on the Home Tab. This updates the view terms pane with the according filtration.

- You can generate the second required action related to the user story "delete a term" through the Search Tab by entering the desired term's name into the search bar and clicking "Search", then clicking the "Remove Term" button. This removal can be observed when clicking the "View All Terms" button again in the Home Tab.

- You can locate my visual component by opening the GUI, where a header image of a dictionary entry will greet you. Additionally, whenever you have a favourite term in your dictionary, it will be displayed in the Home Tab alongside a star image to indicate its status.

- You can save the state of my application by clicking the "Save Dictionary" button in the Home Tab.

- You can reload the state of my application by clicking the "Load" button upon starting the program.

# Phase 4: Task 2

**The following showcases all key events possible in the application's UI**

Event Log: 

Tue Nov 26 17:49:58 PST 2024
Loading dictionary...

Tue Nov 26 17:49:58 PST 2024
Added term: pastel

Tue Nov 26 17:49:58 PST 2024
Added term: hola

Tue Nov 26 17:49:58 PST 2024
Favourited term: hola

Tue Nov 26 17:49:58 PST 2024
Successfully loaded dictionary: My Spanish Dictionary

Tue Nov 26 17:49:58 PST 2024
Viewing all terms

Tue Nov 26 17:50:16 PST 2024
Added term: adios

Tue Nov 26 17:50:16 PST 2024
Favourited term: adios

Tue Nov 26 17:50:19 PST 2024
Successfully found term: hola

Tue Nov 26 17:50:25 PST 2024
Unfavourited term: hola

Tue Nov 26 17:50:28 PST 2024
Successfully found term: pastel

Tue Nov 26 17:50:29 PST 2024
Removed term: pastel

Tue Nov 26 17:50:32 PST 2024
Viewing all terms

Tue Nov 26 17:50:34 PST 2024
Viewing favourite terms

Tue Nov 26 17:50:35 PST 2024
Saved My Spanish Dictionary

# Phase 4: Task 3

If I had more time to work on this project, using the design concepts we've been learning in the recent weeks, theres one thing I mainly would like to change in my design and two ways I could think of putting that into action.

My program passes around a Dictionary instance quite a few times due to the separation between my WelcomeUI and DictionaryUI JFrame classes. In my initial code I had many more dictionary fields as intermediary values that were never used which I eventually factored out as parameters, but there still remains a few extra dictionary fields that I feel could be minimized by having one JFrame class. With this, I would have my button handlers for new and load dictionary in the LoadScreen JPanel class simply remove all panels and then instantiate a new Tab Bar through the WelcomeUI controller field, like done in the DictionaryUI JFrame class. This way, I would end up just having one frame class, omitting the necessity to pass around the dictionary between frames. Using this solution, the dictionary field could exist in the frame class and be accessed by a getter like getDictionary() for when the panel class needs it. 

The second option for resolving this excess in dictionary fields would be to make Dictionary follow the Singleton design pattern. There is only one instance of a dictionary in the entire program and many classes require access to it, so giving it a global access point, like done with the EventLog, could be useful in reducing the amount of classes that need a dictionary field to 1 or even 0. The tradeoff with this solution however, is that it limits development of my application if I wanted to allow the user to register many dictionaries, let's say if there were to be learning multiple languages. However, solely for the state of my program right now, this would be a viable solution.

The reason for solving this problem is mainly to minimize inconsistencies between classes when one class is changed. By having a more "global" dictionary, there is a lesser chance the instance referred to in one class differs from the instance in another class, meaning all changes made by users in the dictionary will be reflected in the program accurately.