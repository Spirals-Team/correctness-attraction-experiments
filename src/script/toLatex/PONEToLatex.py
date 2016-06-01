import sys

import SearchSpaceToLatex

subject=sys.argv[1]

lines = [line.rstrip('\n') for line in
         open("results/" + subject + "/IntegerAddOne_CallExplorer_analysis_graph_data.txt")]
npp = ' '.join(lines[5].split()).split(" ")[1]

out = ""

# (perturbator) IndexLoc   #Perturbations   #Success   #Failure   #Exception (call) %Success
for line in lines[7:]:
    tabline = ' '.join(line.split()).split(" ")
    out +=  tabline[1] + "&" +  tabline[2] + "&" +  tabline[3] + "&" +  tabline[4] + "&"
    out +=  tabline[5] + "&"
    if len(tabline) > 8:
        out +=  SearchSpaceToLatex.barchart(tabline[8]) + " " +  tabline[8]
    else:
        out += tabline[7]
    out += "\\\\\n"

print(out)