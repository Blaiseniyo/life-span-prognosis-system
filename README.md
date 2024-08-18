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
- Get into the project root directory by using the following command: `cd ${project folder name}` eg. `cd life-span-prognosis-system`
- Give execute permissions to all Bash scripts by going into :
` chmod +x src/BashScripts/*.sh`

- Compile the program: 
    - compile the program by running this command in your terminal. Make sure you are in the root directory of the cloned project: `javac -d bin -cp src src/**/*.java`.
  
    The above command should create a bin folder in folders with all the compiled classes.

- Running the program: 
    - Make sure that you are still in the root directory of the app and then run the commnad below:
    - `java -cp bin:Database Main`
