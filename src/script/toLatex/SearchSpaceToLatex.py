
import sys

import LatexToolBox

subjects=("quicksort", "zip", "sudoku", "md5", "rsa", "rc4", "canny", "lcs", "laguerre", "regression" )

exploration=sys.argv[1]
perturbation=sys.argv[2]

def readFilePoints(path):
    lines = [line.rstrip('\n') for line in
             open(path)]
    if len(lines)>1:
        return len(' '.join(lines[1].split()).split(" "))
    else:
        return "-"


out = ""

npp_total=0
executions_total=0
success_total=0.0
nsuperantifragile_total=0
nantifragile_total=0
nfragile_total=0

for subject in subjects:
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/"+exploration+"_CallExplorer_search_space_size_"+perturbation+".txt")]
    npp = ' '.join(lines[2].split()).split(" ")[-1]
    npp_total = npp_total + int(npp)
    executions = ' '.join(lines[3].split()).split(" ")[-1]
    executions_total = executions_total + int(executions)
    success =  ' '.join(lines[5].split()).split(" ")[-1]
    success_total += (float(success.replace(",", ".")) if success != "-" else 0)

    out += subject + "&" + npp + "&" + executions + "&"

    nsuperantifragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_super_anti_fragile.txt")
    nsuperantifragile_total += (int(nsuperantifragile) if nsuperantifragile != "-" else 0)
    nantifragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_anti_fragile.txt")
    nantifragile_total += (int(nantifragile) if nantifragile != "-" else 0)

    oraclefragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_oracle_fragile.txt")
    exceptionfragile = readFilePoints("results/" + subject + "/"+exploration+"_CallExplorer_exception_fragile.txt")
    nfragile = (int(oraclefragile) if oraclefragile != "-" else 0) + (int(exceptionfragile) if exceptionfragile != "-" else 0)
    nfragile_total += int(nfragile)

    out += str(nfragile) + "&" + str(nantifragile) + "&" + str(nsuperantifragile) + "&" + LatexToolBox.barchart(success) + " " + LatexToolBox.simplePerc(success)

    out += "\\\\\n"

success_total = success_total / len(subjects)
success_totalBarChart = '\\textendash' * int(success_total / 10) + " { } " if success_total > 10 else ""

out += "total & " + str(npp_total) + "&" + str(executions_total) + "&" + str(nfragile_total) + "&" + str(nantifragile_total) + "&" + str(nsuperantifragile_total) + "&" + success_totalBarChart + " " + str(success_total)+ " \\%"

print(out)
