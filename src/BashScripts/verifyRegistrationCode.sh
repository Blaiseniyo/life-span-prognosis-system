#!/bin/bash

code=$1

user_line=$(grep "$code" ./Database/users.txt)

if [ -n "$user_line" ]; then
    echo "${user_line}"
else
    echo "Registrations code not found"
fi