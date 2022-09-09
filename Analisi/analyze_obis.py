"""
Script that plots the properties of the spectra observed with the Obis laser.

Author: Giulio Foletto
"""

import numpy as np
import scipy.optimize as sopt
import matplotlib.pyplot as plt
import glob
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("folder", help="Folder where the data files are located")
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

name_pattern = args.folder.replace("\\", "/") + "/*.npz"
file_list = glob.glob(name_pattern)

all_powers = []
all_temperatures = []
all_means = []
all_widths = []
all_areas = []

for file_name in file_list:
    data = np.load(file_name)
    wavelengths = data["x"]
    intensities = data["y"]
    power = float(data["power"])
    temperature = float((str(data["temp"]))[:-1])
    res = analyze_spectrum(wavelengths, intensities)
    all_powers.append(power)
    all_temperatures.append(temperature)
    all_means.append(res["mean"])
    all_widths.append(res["width"])
    all_areas.append(res["area"])

fig = plt.figure()
ax = fig.add_subplot()
plot = ax.scatter(all_powers, all_temperatures, c =all_widths, marker = "o")
ax.set_xlabel("Potenza [W]")
ax.set_ylabel("Temperatura [°C]")
cbar = fig.colorbar(plot, ax = ax)
cbar.ax.set_ylabel('Lunghezza del picco (Std. Dev.) [nm]', rotation=270, labelpad=20)
fig.savefig("linewidthOBIS.pdf", bbox_inches = "tight")

fig2 = plt.figure()
ax2 = fig2.add_subplot()
plot2 = ax2.scatter(all_powers, all_temperatures, c =all_means, marker = "o")
ax2.set_xlabel("Potenza [W]")
ax2.set_ylabel("Temperatura [°C]")
cbar2 = fig2.colorbar(plot2, ax = ax2)
cbar2.ax.set_ylabel('Lunghezza d\'onda media [nm]', rotation=270, labelpad=20)
fig2.savefig("meanOBIS.pdf", bbox_inches = "tight")

fig3 = plt.figure()
ax3 = fig3.add_subplot()
plot3 = ax3.scatter(all_powers, all_temperatures, c =all_areas, marker = "o")
ax3.set_xlabel("Potenza [W]")
ax3.set_ylabel("Temperatura [°C]")
cbar3 = fig3.colorbar(plot3, ax = ax3)
cbar3.ax.set_ylabel('Area del picco [a.u.]', rotation=270, labelpad=20)
fig3.savefig("areaOBIS.pdf", bbox_inches = "tight")

plt.tight_layout()
plt.show()