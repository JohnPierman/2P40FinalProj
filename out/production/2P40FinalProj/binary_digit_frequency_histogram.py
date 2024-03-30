import matplotlib.pyplot as plt
from collections import Counter

# Data
data = [
    1, 2, 2, 4, 5, 2, 7, 8, 9, 2, 11, 12, 13, 14, 15, 16, 16, 2, 2, 4, 4, 2, 2, 8, 8, 2, 2, 12, 12, 14, 14, 32, 33, 34, 34,
    36, 37, 2, 34, 40, 41, 2, 43, 44, 13, 14, 14, 32, 32, 34, 34, 32, 32, 34, 34, 56, 56, 34, 42, 56, 56, 42, 2, 64, 65,
    2, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 16, 16, 2, 2, 4, 4, 2, 2, 8, 8, 2, 2, 12, 12, 14, 14, 96, 97,
    34, 34, 100, 101, 2, 103, 104, 105, 106, 107, 76, 109, 78, 14, 32, 32, 34, 34, 32, 32, 34, 34, 56, 56, 42, 34, 124, 124,
    2, 14, 128, 128, 130, 130, 128, 128, 130, 130, 128, 128, 128, 130, 128, 128, 128, 130, 128, 128, 130, 130, 128, 128,
    130, 130, 128, 128, 160, 160, 162, 162, 128, 160, 162, 162, 128, 169, 162, 162, 128, 128, 130, 2, 160, 160, 162, 162,
    160, 160, 162, 162, 160, 160, 170, 170, 160, 160, 162, 162, 128, 128, 130, 130, 128, 128, 130, 130, 128, 128, 130, 130,
    128, 128, 130, 130, 128, 128, 160, 160, 162, 162, 160, 229, 162, 162, 232, 233, 162, 162, 128, 128, 2, 162, 160, 160,
    162, 162, 160, 160, 162, 162, 160, 160, 170, 162, 160, 160, 162, 162
]

def count_on_bin_digits(numbers):
    bin_digit_count = Counter()
    for number in numbers:
        bin_rep = bin(number)[2:]  # Convert to binary and remove '0b' prefix
        for i, digit in enumerate(reversed(bin_rep), 1):  # Start counting positions from 1
            if digit == '1':
                bin_digit_count[i] += 1  # Increment count of the position if digit is '1'
    return bin_digit_count

bin_digit_frequency = count_on_bin_digits(data)

positions, frequencies = zip(*sorted(bin_digit_frequency.items()))

plt.figure(figsize=(10, 6))
plt.bar(positions, frequencies, width=0.5)
plt.xlabel('Algorithm')
plt.ylabel('Frequency')
plt.title('Survivability Frequency')
plt.xticks(positions)
plt.show()
