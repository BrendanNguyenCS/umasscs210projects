# UMass Boston CS210 (Intermediate Computing with Data Structures)

Last Updated: December 2022

This is a remote repository for all of my UMass Boston Intermediate Computing with Data Structures (CS210) class projects and midterm exam. I don't have the final exam as I chose to complete project 6 as a replacement for the final exam's grade. View at your discretion keeping in mind that these may or may not be perfect solutions but work nonetheless.

Note: The `cmd` statements needed to run these programs may differ from those listed in the project descriptions PDF. Since the `algs4` libraries have replaced the `stdlib` and `dsa` libraries that existed at the time of the class, the code has been adjusted as good as possible to utilize the modified classes that come with `algs4`. The `algs4` library is meant to be used alongside Algorithms 4th Edition by Robert Sedgewick and Kevin Wayne.

## Compiling the Code

In order to compile the code, `cd` to the src folder (or where the root of your source code is) and run the following command. This example compiles code in project1.

```console
javac -d ../classes project1/*.java
```

This will compile all Java files in project 1 and put the `.class` files in the `../classes/project` subdirectory.

## Running Programs

In order to run programs, `cd` to the classes subdirectory and run the following command. The Windows command is shown, other OS's will replace `;` with `:`. This also loads in the `algs4` library in order to utilize its objects. The example below runs the Great Circle Distance program from Project 1 Exercise 1.

```console
java -cp .;../lib/algs4.jar project1.GreatCircle 48.87 -2.33 37.8 -122.4
```

It is important to add the correct file path for any external files that you want to pass into your programs. In this project, all text files were added to the `classes` subdirectory, put into their respective project. Since you are running the commands from `classes`, you have to provide the path of the file from there (i.e. `project1/input1.txt`).

