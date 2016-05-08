import numpy as np
import matplotlib.pyplot as plt

import sys

def buildHeatMap(subject):

    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/IntegerAddM_RandomExplorer_analysis_graph_data.txt")]
    magnitudes = ' '.join(lines[2].split()).split(" ")[3:]
    randoms = ' '.join(lines[5].split()).split(" ")[3:]
    numberOfLocation = ' '.join(lines[3].split()).split(" ")[0]

    y = [float(rnd) for rnd in randoms]
    x = [int(mag) for mag in magnitudes]

    print(x)
    print(y)

    i = 9
    nbLocation = 0
    mx, my = np.meshgrid(x, y)

    for loc in range(int(numberOfLocation)):
        currentLoca = int(' '.join(lines[i].split()).split(" ")[2])
        intensity = []
        for zy in range(len(y)):
            currentIntensity = []
            for zx in range(len(x)):
                perc = float(' '.join(lines[i + zx].split()).split(" ")[-1].replace(',', '.'))
                if perc == perc:
                    currentIntensity.append(int(perc))
                else:
                    currentIntensity.append(100)

            intensity.append(currentIntensity)
            i = i + len(y) + 1


        b = True
        for intensities in intensity:
            for intens in intensities:
                b = b and intens == 100

        if not b:
            print(intensity)

            intensity = np.array(intensity)

            fig = plt.figure()
            ax = fig.add_axes((.1,.4,.8,.5))
            box = ax.get_position()
            ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
            plt.pcolormesh(my, mx, intensity, label="%Success")
            plt.colorbar()
            plt.xlabel("probability")
            plt.ylabel("magnitude")
            txt = "Heat map for the location " + str(currentLoca) + " on " + subject
            text = fig.text(.1,.25,txt)
            plt.title(subject)
            fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+".pdf", bbox_extra_artists=(text,), bbox_inches='tight')
            fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+".jpeg")
            ax.set_xscale('log')
            ax.set_yscale('log')
            fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+"_log.pdf")
            fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+"_log.jpeg")

subjects=sys.argv[1:]
for subject in subjects:
    buildHeatMap(subject)