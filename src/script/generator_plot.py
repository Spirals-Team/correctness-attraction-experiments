from matplotlib import pyplot as plt

def genPlotAsPdf(path, filename, subject):

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]
    title = ' '.join(lines[0].split()).split(" ")[0]

    labelOfN = ' '.join(lines[1].split()).split(" ")[0]
    n = ' '.join(lines[1].split()).split(" ")[3:]
    numberOfLocation = int(' '.join(lines[2].split()).split(" ")[0])

    percAll=[]
    indicesLocation=[]
    i = 9
    currentLoc = 0

    while currentLoc != numberOfLocation:

        indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]
        indicesLocation.append(indexOfLocation)

        perc=[]

        for line in lines[i:i+len(n)]:
            perc.append(float(' '.join(line.split()).split(" ")[-1].replace(',','.')))

        percAll.append(perc)

        i+=len(n)
        currentLoc += 1

    fig, ax = plt.subplots(nrows=1, ncols=1)
    for i in range(len(percAll)):
        plt.plot(n, percAll[i], marker='x', label="location"+str(indicesLocation[i]))

    plt.xlabel(labelOfN)
    plt.ylabel("% success")
    plt.title(title+"_"+subject)

    box = ax.get_position()
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/"+labelOfN+"_plot.pdf")
    plt.close(fig)

genPlotAsPdf("results/sort", "RndExplorer_random_rates_analysis_graph_data", "sort")
genPlotAsPdf("results/sort", "AddNExplorer_magnitude_analysis_graph_data", "sort")
'''
genPlotAsPdf("results/zip", "AddNExplorer_perRates", "zip")
genPlotAsPdf("results/zip", "RndExplorer_perRates", "zip")
genPlotAsPdf("results/md5", "RndExplorer_perRates", "md5")
genPlotAsPdf("results/md5", "AddNExplorer_perRates", "md5")
'''
