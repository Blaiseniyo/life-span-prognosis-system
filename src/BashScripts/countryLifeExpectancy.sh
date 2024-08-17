#!/bin/bash

country_name=$1

# Function to search life expectancy by country
function get_life_expectancy {
  local country_name="$1"
  local file_path="./Database/life-expectancy.csv"
  # Use awk to parse the CSV file and extract life expectancy for the given country
  awk -F, -v country="$country_name" '
    BEGIN {IGNORECASE=1} # Case-insensitive matching
    NR > 1 && $1 == country {print$NF; exit}' ${file_path}
}

# Call the function with the user input
get_life_expectancy "$country_name"