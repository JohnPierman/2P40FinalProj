
import matplotlib.pyplot as plt
import numpy as np

# Data
data = [
    7, 6, 6, 5, 5, 6, 6, 4, 4, 6, 6, 4, 4, 6, 6, 3, 3, 6, 6, 5, 5, 6, 6, 4, 4, 6, 6, 4, 4, 6, 6, 2, 2, 2, 2, 2, 2,
    6, 6, 2, 2, 6, 2, 4, 4, 6, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 6, 6, 1, 1, 6, 6, 1, 1, 6, 6, 1, 1,
    6, 6, 3, 3, 6, 6, 5, 5, 6, 6, 4, 4, 6, 6, 4, 4, 6, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 6, 6, 2, 2,
    2, 2, 2, 2, 2, 2, 2, 2, 2, 6, 6, 0, 0, 6, 6, 0, 0, 6, 6, 0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 6, 6, 0, 0, 6, 6, 0, 0,
    6, 6, 0, 0, 6, 6, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 0, 0, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0,
    0, 6, 6, 0, 0, 6, 6, 0, 0, 6, 6, 0, 0, 6, 6, 0, 0, 6, 6, 0, 0, 6, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0,
    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0
]

# Calculate the frequency of each value in the data
values, counts = np.unique(data, return_counts=True)

# Create a bar graph
plt.figure(figsize=(10, 6))
plt.bar(values, counts, color='skyblue', edgecolor='black')
plt.title('Dominance Frequency')
plt.xlabel('Algorithm')
plt.ylabel('Frequency')
plt.grid(axis='y', linestyle='--', linewidth=0.7)
plt.tight_layout()

# Show the bar graph
plt.show()
