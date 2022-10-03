"""
Script that plots one file generated by the spectrometer.

Author: Giulio Foletto
"""

import numpy as np
import scipy.optimize as sopt
import matplotlib.pyplot as plt
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("file", help="Name of the file containing the data")
args = parser.parse_args()

def gaussian_with_background(x, height, mean, width, background):
    return height*np.exp(-0.5*(x-mean)**2/width**2)+background

def analyze_spectrum(wavelengths, intensities):
    # Preliminary analysis
    # Background is estimated from the first ten points, assuming that the peak is not there
    background = np.mean(intensities[:10])
    # index_max is the index (in the array) corresponding to the maximum intensity
    index_max = np.argmax(intensities)
    # Wavelength of the maximum intensity
    wavelength_max = wavelengths[index_max]
    # Value of the maximum intensity
    intensity_max = intensities[index_max]
    # Step (in nm) of the wavelength array
    step = wavelengths[1]-wavelengths[0]
    # Initial guess of the parameters
    p0 = [intensity_max-background, wavelength_max, step, background]
    # Perform the fit
    res, _ = sopt.curve_fit(gaussian_with_background, wavelengths, intensities, p0 = p0)
    # Return the results
    return {"height": res[0], 
            "mean": res[1], 
            "width": res[2],
            "background": res[3],
            "area": res[0]*res[2]*np.sqrt(2*np.pi), # Due to properties of the gaussian function
            "pre_height": p0[0],
            "pre_mean": p0[1],
            "pre_width": p0[2],
            "pre_background": p0[3],
            "pre_area": np.sum(intensities[index_max-3:index_max+3])-6*p0[3]}

file_name = args.file
data = np.load(file_name)
wavelengths = data["x"]
intensities = data["y"]
power = float(data["power"])
temperature = float((str(data["temp"]))[:-1])
res = analyze_spectrum(wavelengths, intensities)

fig = plt.figure()
ax = fig.add_subplot()
ax.plot(wavelengths, intensities, label = "Data")
x_range = np.linspace(wavelengths.min(), wavelengths.max(), 20*wavelengths.size)
ax.plot(x_range, gaussian_with_background(x_range, res["height"], res["mean"], res["width"], res["background"]), label = "Best fit")
ax.set_xlabel("Lunghezza d'onda [nm]")
ax.set_ylabel("Intensità spettrale [a.u.]")
ax.legend()
ax.grid()
fig.savefig("one_plot.pdf", bbox_inches = "tight")

plt.tight_layout()
plt.show()