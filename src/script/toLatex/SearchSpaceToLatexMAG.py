
import sys

import LatexToolBox

#subjects=("quicksort", "zip", "sudoku", "md5", "TEA", "rc4", "laguerre", "rsa", "classifier", "torrent", "bitcoin") #11 project, probably remove TEA
subjects=("quicksort", "zip", "sudoku", "md5")#, "rc4", "laguerre", "rsa", "classifier")#, "canny")#, "lcs")#, "torrent", "bitcoin") #11 project, probably remove TEA

exploration=sys.argv[1]
perturbation=sys.argv[:2]

def readFilePoints(path):
    lines = [line.rstrip('\n') for line in
             open(path)]
    if len(lines)>1:
        return len(' '.join(lines[1].split()).split(" "))
    else:
        return "-"


out = ""

for subject in subjects:
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/"+exploration+"_CallExplorer_search_space_size_"+perturbation+".txt")]
    npp = ' '.join(lines[2].split()).split(" ")[-1]
    executions = ' '.join(lines[3].split()).split(" ")[-1]
    success =  ' '.join(lines[5].split()).split(" ")[-1]

    out += subject + "&" + npp + "&" + executions + "&"

    nsuperantifragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_super_anti_fragile.txt")
    nantifragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_anti_fragile.txt")

    oraclefragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_oracle_fragile.txt")
    exceptionfragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_exception_fragile.txt")
    nfragile = (int(oraclefragile) if oraclefragile != "-" else 0) + (int(exceptionfragile) if exceptionfragile != "-" else 0)

    out += str(nfragile) + "&" + str(nantifragile) + "&" + str(nsuperantifragile) + "&" + LatexToolBox.barchart(success) + " " + LatexToolBox.simplePerc(success)

    out += "\\\\\n"

print(out)
