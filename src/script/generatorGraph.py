from matplotlib import pyplot as plt

def genPlotAsPdf(path, filename, subject):

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]
    title = ' '.join(lines[0].split()).split(" ")[0]

    labelOfN = ' '.join(lines[1].split()).split(" ")[0]
    n = ' '.join(lines[1].split()).split(" ")[3:]
    numberOfLocation = int(' '.join(lines[2].split()).split(" ")[0])

    percAll=[]
    i = 9
    currentLoc = 0

    while currentLoc != numberOfLocation:

        indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]

        perc=[]

        for line in lines[i:i+len(n)]:
            perc.append(float(' '.join(line.split()).split(" ")[-1].replace(',','.')))

        if not all(p == 0 for p in perc):

            percAll.append(perc)

            fig, ax = plt.subplots(nrows=1, ncols=1)

            plt.plot(n, perc, label="% of success in function of " + str(labelOfN), marker='x')

            plt.xlabel(labelOfN)
            plt.ylabel("% success")
            plt.title(title+"_"+subject)
            plt.legend(shadow=True, fancybox=True)
            fig.savefig(path+"/img/"+labelOfN+"_plot_"+str(indexOfLocation)+".pdf")
            plt.close(fig)

        i+=len(n)
        currentLoc += 1

    fig, ax = plt.subplots(nrows=1, ncols=1)
    for i in range(len(percAll)):
        plt.plot(n, percAll[i], marker='x')

    plt.xlabel(labelOfN)
    plt.ylabel("% success")
    plt.title(title+"_"+subject)
    fig.savefig(path+"/img/"+labelOfN+"_plot_All.pdf")
    plt.close(fig)

genPlotAsPdf("results/zip", "RndExplorer_perRates", "zip")
genPlotAsPdf("results/zip", "AddNExplorer_perRates", "zip")
genPlotAsPdf("results/sort", "RndExplorer_perRates", "sort")
genPlotAsPdf("results/sort", "AddNExplorer_perRates", "sort")
genPlotAsPdf("results/md5", "RndExplorer_perRates", "md5")
genPlotAsPdf("results/md5", "AddNExplorer_perRates", "md5")

