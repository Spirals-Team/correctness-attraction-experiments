from matplotlib import pyplot as plt

import colors_manager

import sys


def plot(subject, type):

    print(subject, type)

    lines = [line.rstrip('\n') for line in open("results/"+subject+"/"+type+"_RandomExplorer_analysis_graph_data.txt")]

    numberOfLocation = int(' '.join(lines[7].split()).split(" ")[0])
    n = [0.0]  + (' '.join(lines[3].split()).split(" ")[3:])
    repeat = int(' '.join(lines[4].split()).split(" ")[0])
    i = 9

    percAll=[]
    nAll=[]
    callAll=[]
    perturbAll=[]
    indicesLocation=[]
    percAll=[]
    xaxis=[]

    while i < (numberOfLocation*len(n)) + 1:

        perc=[]
        my_n = []
        my_x = []
        my_call = 0
        my_perturb = 0

        for line in lines[i:i+len(n)]:
            point = float(' '.join(line.split()).split(" ")[-1].replace(',','.'))
            if point == point:
                perc.append(point)
            else:
                perc.append(float(100.0))
            my_call += int(' '.join(line.split()).split(" ")[6].replace(',','.'))
            my_perturb += int(' '.join(line.split()).split(" ")[7].replace(',','.'))
            my_x.append(int(' '.join(line.split()).split(" ")[7].replace(',','.')) / repeat)
            my_n.append(n[lines[i:i+len(n)].index(line)])

        if perc not in percAll and len(perc) > 0 and [p == p for p in perc]:
            indexOfLocation = ' '.join(lines[i].split()).split(" ")[2]
            indicesLocation.append(indexOfLocation)
            percAll.append(perc)
            nAll.append(my_n)
            callAll.append(my_call)
            perturbAll.append(my_perturb)
            xaxis.append(my_x)

        i+=len(n)


    sortedPerc, indicesLocation = [list(x)[:10] for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][-1]))]
    sortedPerc, callAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, callAll), key=lambda pair: -pair[0][-1]))]
    sortedPerc, perturbAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, perturbAll), key=lambda pair: -pair[0][-1]))]
    sortedPerc, xaxis = [list(x)[:10] for x in zip(*sorted(zip(percAll,xaxis), key=lambda pair: -pair[0][-1]))]
    percAll, nAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][-1]))]

    sortedPerc, indicesLocation = [list(x) for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][0]))]
    sortedPerc, callAll = [list(x) for x in zip(*sorted(zip(percAll, callAll), key=lambda pair: -pair[0][0]))]
    sortedPerc, perturbAll = [list(x) for x in zip(*sorted(zip(percAll, perturbAll), key=lambda pair: -pair[0][0]))]
    sortedPerc, xaxis = [list(x) for x in zip(*sorted(zip(percAll, xaxis), key=lambda pair: -pair[0][0]))]
    percAll, nAll = [list(x) for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][0]))]

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(percAll)):
        color = colors_manager.getColor(int(indicesLocation[i]))
        plt.plot(xaxis[i], percAll[i], marker='x', label=str(indicesLocation[i]+ " " + str(int(percAll[i][0])) + " %"), color=color)
    plt.xlabel("#perturbation per exec")
    plt.ylabel("% success")
    box = ax.get_position()
    txt = ""
    for line in lines[0:7]:
        txt += line +"\n"
    width = 8
    txt+= '{0:{width}} {1:{width}} {2:{width}}'.format("Loc","#Call", "#Perturbation", width=width)
    txt+='\n'
    for i in range(len(callAll)):
        txt+='{0:{width}} {1:{width}} {2:{width}}'.format(indicesLocation[i],str(callAll[i]),str(perturbAll[i]), width=width)
        txt+='\n'

    text = fig.text(.1,-.45,txt)
    plt.title(subject)
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig("results/"+subject+"/img/"+type+"_PerturPerExecution_plot.jpeg", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    fig.savefig("results/"+subject+"/img/"+type+"_PerturPerExecution_plot.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    ax.set_xscale('log')
    fig.savefig("results/"+subject+"/img/"+type+"_PerturPerExecution_plot_logscale.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    fig.savefig("results/"+subject+"/img/"+type+"_PerturPerExecution_plot_logscale.jpeg", bbox_extra_artists=(lgd,text), bbox_inches='tight')

subjects=sys.argv[1:]

for subject in subjects:
    plot(subject, "IntegerAddOne")
    plot(subject, "BooleanNegation")