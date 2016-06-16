import sys

import LatexToolBox

subject=sys.argv[1]

# exploration="IntegerAddOne"
exploration="IntegerMinusOne"
#exploration="BooleanNegation"

lines = [line.rstrip('\n') for line in
         open("results/" + subject + "/"+exploration+"_CallExplorer_analysis_graph_data.txt")]
npp = ' '.join(lines[5].split()).split(" ")[0]

out = ""

# (perturbator) IndexLoc   #Perturbations   #Success   #Failure   #Exception (call) %Success
for line in lines[7:]:
    tabline = ' '.join(line.split()).split(" ")
    out +=  tabline[1] + "&" +  tabline[2] + "&" +  tabline[3] + "&" +  tabline[4] + "&"
    out +=  tabline[5] + "&"
    if len(tabline) > 8:
        out +=  LatexToolBox.barchart(tabline[8]) +   LatexToolBox.simplePerc(tabline[8])
    else:
        out += LatexToolBox.simplePerc(tabline[7])
    out += "\\\\\n"

print(out)