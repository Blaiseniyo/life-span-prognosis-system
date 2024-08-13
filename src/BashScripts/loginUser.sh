#!/bin/bash

email=$1
password=$2

# echo "Debug: Searching for email: $email" >&2

# Find the user by email using grep and cut
user_line=$(grep "$email" ./Database/users.txt)

# echo "Debug: Grep result: $user_line" >&2


salt=5c10a16899ff96fd

if [ -n "$user_line" ]; then
    # echo "Debug: User found" >&2
    stored_password=$(echo "$user_line" | cut -f6)
    
    # echo "Debug: Stored password: $stored_password" >&2
    
    # Decrypt the stored password
    user_password=$(openssl passwd -6 -salt "$salt"  "$password")
    # echo "Debug: Decrypted password: $user_password" >&2
    # echo "Debug: Provided password: $password" >&2
    
    if [ "$user_password" = "$stored_password" ]; then
        echo "$user_line"
    else
        echo "Invalid password"
    fi
else
    echo "User not found"
fi