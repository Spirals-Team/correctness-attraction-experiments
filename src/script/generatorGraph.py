from matplotlib import pyplot as plt

def genPlotAsPdf(path, filename):

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]
    title = ' '.join(lines[0].split()).split(" ")[0]

    labelOfN = ' '.join(lines[1].split()).split(" ")[0]
    n = ' '.join(lines[1].split()).split(" ")[2:]

    numberOfLocation = int(' '.join(lines[2].split()).split(" ")[0])

    i = 8
    currentLoc = 0

    while currentLoc != numberOfLocation:

        indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]

        perc=[]

        for line in lines[i:i+len(n)]:
            perc.append(float(' '.join(line.split()).split(" ")[-1].replace(',','.')))

        fig, ax = plt.subplots(nrows=1, ncols=1)
        plt.plot(n, perc, label="% of success in function of " + str(labelOfN), marker='x')
        plt.xlabel(labelOfN)
        plt.ylabel("% success")
        plt.title(title)
        plt.legend(shadow=True, fancybox=True)
        fig.savefig(path+"/img/"+labelOfN+"_plot_"+str(indexOfLocation)+".pdf")
        plt.close(fig)

        i+=len(n)
        currentLoc += 1


genPlotAsPdf("results/zip", "RndExplorer_perN")
genPlotAsPdf("results/zip", "AddNExplorer_perN")
genPlotAsPdf("results/sort", "RndExplorer_perN")
genPlotAsPdf("results/sort", "AddNExplorer_perN")


