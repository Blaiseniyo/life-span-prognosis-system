# Life Prognosis Management Tool

## Overview

The Life Prognosis Management Tool is a command-line application for managing user registrations and logins in a health prognosis system. It caters to both administrators and patients, offering user registration, data management, and secure authentication.

## Requirements

- Java Development Kit (JDK) 8 or higher
- Bash-compatible shell
- OpenSSL


## Setup and Installation

- Clone the repository or download the project files: 
 `git clone https://github.com/Blaiseniyo/life-span-prognosis-system.git`

- Give execute permissions to all Bash scripts:
` chmod +x src/BashScripts/*.sh`

- Compile the program: 
    -  cd in the project working directory. eg: `cd Lifiplan\ Prognosis\ Project/ `
    - compile the program by running this command in your terminal. Make sure you are in the working directory of the cloned project: `javac -d bin -cp src src/**/*.java`.

    The above command should create a bin folder in folders with all the compile classes.

- Running the program: 
    - Make sure that you are still in the root directory of the app and then commnad below:

    - `java -cp bin:Database Main`

` `