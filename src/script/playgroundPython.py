
subject = "quicksort"

lines = [line.rstrip('\n') for line in open("results/"+subject+"/IntegerAddOne_CallExplorer_analysis_graph_data.txt")]

arrayNbExec=[]
maxExec = -1
minExec = -1
for line in lines[7:]:
    nbExec = int(' '.join(line.split()).split(" ")[2])
    if minExec == -1 or nbExec < minExec:
        minExec = nbExec
    elif maxExec < nbExec:
        maxExec = nbExec
    arrayNbExec.append(nbExec)

arrayNbExec.sort()
print("min " + str(minExec))
print("max " + str(maxExec))
print("med " + str(arrayNbExec[len(arrayNbExec) / 2]))

print("avg min " + str(float(minExec / 20.0)))
print("avg max " + str(float(maxExec / 20.0)))
print("avg med " + str(float(arrayNbExec[len(arrayNbExec) / 2] / 20.0)))
