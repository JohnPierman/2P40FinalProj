import pandas as pd
import matplotlib.pyplot as plt
import re
from numpy.polynomial.polynomial import Polynomial
import numpy as np

def parse_file(filepath):
    data = pd.DataFrame(columns=['Round', 'Group', 'Population'])

    with open(filepath, 'r') as file:
        current_round = 0
        for line in file:
            print("parsing line: " + str(current_round))
            if 'Round:' in line:
                current_round = int(re.search(r'Round: (\d+)', line).group(1))
            elif 'Name:' in line and 'Population:' in line:
                parts = line.split()
                group_name = parts[1]  # Assuming the group name follows 'Name:'
                population = int(re.search(r'Population: (\d+)', line).group(1))
                data = pd.concat([data, pd.DataFrame({'Round': [current_round], 'Group': [group_name], 'Population': [population]})], ignore_index=True)

    return data

def plot_data(data):
    plt.figure(figsize=(15, 10))

    # Calculate total population per round
    total_population_per_round = data.groupby('Round')['Population'].sum()

    for group in data['Group'].unique():
        group_data = data[data['Group'] == group].copy()
        x = pd.to_numeric(group_data['Round'])

        # Calculate percentage of total population
        group_data['Percentage'] = group_data.apply(lambda row: (row['Population'] / total_population_per_round.loc[row['Round']]) * 100, axis=1)
        y = group_data['Percentage']

        # Apply smoothing
        #y_smooth = smooth_data(x, y, 20)

        #plt.plot(x, y_smooth, label=group)
        plt.plot(x, y, label=group)
    plt.xscale('log')
    plt.xlabel('log_10(Round)')
    plt.ylabel('Population')
    plt.title('Population of Each Group Across Rounds')
    plt.legend()
    plt.grid(True)
    plt.show()

# Replace 'path_to_your_data_file.txt' with the actual path to your data file
data_file = 'dataToVisualize.txt'
data = parse_file(data_file)
plot_data(data)