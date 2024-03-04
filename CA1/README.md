# Techincal Report Of CA1

First Week - No Branches

In this first part of this assignment, the main goals were to create a new folder with a copy of the tutorial and then develop and test a new feature, all the while working on the main branch.

- To respond to the first step, while in the folder for this repository, the command "mkdir CA1" was used. To copy the tutorial to the new folder, the command "cp -r --exclude=".git" " was used to copy everything but the .git file.
- The tag v1.1.0 was added to the project, using the commands "git tag v1.1.0" and then "git push --tag". Had to make some adjustments with the folder placement, so the tag was placed in the last immediate last commit before starting to implement the code changes.
- Then, the java code provided in the class "Employee" was altered accordingly to add the feature of "jobYears" as well as its verification methods. Also, the DatabaseLoader and app.js classes were updated to reflect these changes once the application runs. Please check commit 68825a8 for further detail. To push all the updates to this repository, the commands "git add ." followed by "git commit -m "Descriptive message for each commit" " and lastly "git push origin main" were used.
- The unit tests were added to validate the parameters to create the Employee object (commit ae9bf9b).
- The debugging took place on both client (using Chrome's "React Developer Tools" extension) and server (using the Intellij IDE).
- Lastly, the tag v1.2.0 was pushed to the repository, using the same commands as before.

Notes: Since I've started by developing the exercise suggested in the slides 15, 16 and 17 of the theoretical document provided, before starting this class assignment, the code itself present some extra changes that accommodate that exercise, not only the one requested for this assingment.
