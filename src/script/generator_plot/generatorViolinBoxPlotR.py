
import sys

exploration=sys.argv[1]

subjects=("quicksort", "zip", "sudoku", "md5", "rc4", "laguerre", "rsa", "classifier", "canny")[::-1]#11 project, probably remove TEA

outputR = open('results/violin_boxplot'+exploration+'.R', 'w')

outputR.write("pdf(\"violinboxplot_"+exploration+".pdf\")\n")

out = "names <- c("
for s in subjects[:-1]:
    out += "\""+ s + "\"" + ","
out += "\""+ subjects[-1] + "\")\n"

outputR.write(out)

for subject in subjects:
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/"+exploration+"_CallExplorer_analysis_graph_data.txt")]

    npp = ' '.join(lines[5].split()).split(" ")[0]
    success = []
    for line in lines[7:]:
        value = float(' '.join(line.split()).split(" ")[-1].replace(",", "."))
        if value == value:
            success.append(value)

    success = sorted(success)

    out = subject + " <- c("
    for s in success[:-1]:
        out += str(s) + ","
    out += str(success[-1])+")"
    outputR.write(out + "\n")

out = "par(yaxt=\"n\")\n"
out += "vioplot("
for s in subjects:
    out += s + ","
out += "names=names, col=\"lightblue\", horizontal=TRUE, drawRect=FALSE)\n"
out += "axis(2, at=seq(1, "+str(len(subjects))+", by=1), labels = FALSE)\n"
out += "text(y = seq(1, "+str(len(subjects))+", by=1), par(\"usr\")[1], labels = names, srt = 0, pos = 2, xpd = TRUE)\n"
out += "title(ylab=\"\", xlab=\"%success\")\n"
out += "dev.off()\n"
outputR.write(out)
outputR.close()