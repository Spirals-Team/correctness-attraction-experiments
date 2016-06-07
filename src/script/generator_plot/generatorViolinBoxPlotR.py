subjects=("quicksort", "zip", "sudoku", "md5", "rc4", "laguerre", "rsa", "classifier", "torrent", "bitcoin")[::-1]#11 project, probably remove TEA

outputR = open('results/violin_boxplot.R', 'w')

outputR.write("pdf(\"violinboxplot.pdf\")\n")

for subject in subjects:
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/IntegerAddOne_CallExplorer_analysis_graph_data.txt")]

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

out = "vioplot("
for s in subjects:
    out += s + ","
out += "names=c("
for s in subjects[:-1]:
    out += "\""+s+"\"" + ","
out += "\""+subjects[-1]+ "\""+"), col=\"gold\", horizontal=FALSE)"
#out += "\""+subjects[-1]+ "\""+"), col=\"gold\", horizontal=TRUE)"

outputR.write(out + "\n")
outputR.write("dev.off()")
outputR.close()