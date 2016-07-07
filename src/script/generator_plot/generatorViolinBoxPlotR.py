
import sys

exploration=sys.argv[1]

subjects=("quicksort", "zip", "sudoku", "md5", "rsa", "rc4", "canny", "lcs", "laguerre", "regression" )[::-1] #NEED TO ADD LCS

outputR = open('results/violin_boxplot'+exploration+'.R', 'w')

outputR.write("pdf(\"violinboxplot_"+exploration+".pdf\")\n")
outputR.write("mar.default <- c(5,4,4,2) + 0.1\n")
outputR.write("par(mar = mar.default + c(0, 4, 0, 0))\n")

out = "names <- c("
for s in subjects[:-1]:
    out += "\""+ s + "\"" + ","
out += "\""+ subjects[-1] + "\")\n"

outputR.write(out)

points = []

for i in range(len(subjects)):
    subject = subjects[i]
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/"+exploration+"_CallExplorer_analysis_graph_data.txt")]

    npp = ' '.join(lines[5].split()).split(" ")[0]
    success = []
    for line in lines[7:]:
        value = float(' '.join(line.split()).split(" ")[-1].replace(",", "."))
        if value == value:
            success.append(value)

    success = sorted(success)

    points.append((i, success[int(len(success) / 2)]))

    out = subject + " <- c("
    for s in success[:-1]:
        out += str(s) + ","
    out += str(success[-1])+")"
    outputR.write(out + "\n")

out = "par(yaxt=\"n\")\n"
out += "vioplot("
for s in subjects:
    out += s + ","
out += "names=names, col=\"lightblue\", horizontal=TRUE, drawRect=FALSE, h=5)\n"
for i in range(len(subjects)):
    out += "points("+str(points[i][1])+","+str(i+1)+", col=\"white\", bg=\"white\", type=\"p\", pch = 21)\n"
out += "axis(2, at=seq(1, "+str(len(subjects))+", by=1), labels = FALSE)\n"
out += "text(y = seq(1, "+str(len(subjects))+", by=1), par(\"usr\")[1], labels = names, srt = 0, pos = 2, xpd = TRUE)\n"
out += "title(ylab=\"\", xlab=\"Correctness ratio\")\n"
out += "dev.off()\n"
outputR.write(out)
outputR.close()