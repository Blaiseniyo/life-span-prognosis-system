#!/bin/bash

# Input and output file names
input_file="./Database/users.txt"
output_file=$1

# Check if input file exists
if [ ! -f "$input_file" ]; then
    echo "Error: Input file $input_file not found."
    exit 1
fi

# Convert tab-separated file to CSV
awk -F'\t' '{
    if ($5 == "PATIENT") {
    for (i=1; i<=NF; i++) {
        if (i > 1) printf ","
        if ($i ~ /,|"/) {
            gsub(/"/, "\"\"", $i)
            printf "\"%s\"", $i
        } else {
            printf "%s", $i
        }
    }
    printf "\n"
    }
}' "$input_file" > "$output_file"

echo "Conversion complete. Output saved to $output_file"
touch $filename