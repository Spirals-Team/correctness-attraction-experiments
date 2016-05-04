import numpy as np
import matplotlib.pyplot as plt

subject = "quicksort"

lines = [line.rstrip('\n') for line in
         open("results/" + subject + "/IntegerAddM_RandomExplorer_analysis_graph_data.txt")]
magnitudes = ' '.join(lines[2].split()).split(" ")[3:]
randoms = ' '.join(lines[5].split()).split(" ")[3:]
numberOfLocation = ' '.join(lines[3].split()).split(" ")[0]

x = [float(rnd) for rnd in randoms]
y = [int(mag) for mag in magnitudes]

i = 9
nbLocation = 0
mx, my = np.meshgrid(x, y)

for loc in range(int(numberOfLocation)):
    currentLoca = int(' '.join(lines[i].split()).split(" ")[2])
    intensity = []
    for zy in range(len(y)):
        currentIntensity = []
        for zx in range(len(x)):
            currentIntensity.append(int(float(' '.join(lines[i + zx].split()).split(" ")[-1].replace(',', '.'))))
        intensity.append(currentIntensity)
        i = i + len(y)

    print(x)
    print(y)
    print(intensity)

    intensity = np.array(intensity)

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    plt.pcolormesh(mx, my, intensity)
    plt.colorbar()
    fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+".pdf")
    fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+".jpeg")
    ax.set_xscale('log')
    ax.set_yscale('log')
    fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+"_log.pdf")
    fig.savefig("results/" + subject + "/img/heatmap_"+str(currentLoca)+"_log.jpeg")

