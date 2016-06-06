

subjects=("quicksort", "zip", "sudoku", "md5", "rc4", "laguerre", "rsa", "classifier", "torrent", "bitcoin")[::-1]#11 project, probably remove TEA

latexboxplot = open('results/data_boxplot', 'w')

header = "\\begin{tikzpicture}\n\\begin{axis}\n[\nytick={"\

for i in range(len(subjects)-1):
    header += str(i+1) + ','
header += str(len(subjects))+"},\nyticklabels={"
for subject in subjects[:-1]:
    header += subject + ","

header += subjects[-1]+"},\n]\n"

latexboxplot.write(header)

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
    median = success[int(len(success) * 0.5)]
    firstQ = success[int(len(success) * 0.25)]
    thirdQ = success[int(len(success) * 0.75)]
    min_s = success[0]
    max_s = success[-1]

    out = "\\addplot+[\nboxplot prepared={\n\tmedian="+str(median)+",\n\tupper quartile="+str(thirdQ)+",\n\tlower quartile="+str(firstQ)+",\n\tupper whisker="+str(max_s)+",\n\tlower whisker="+str(min_s)+"\n},\n] coordinates {};\n"

    latexboxplot.write(out)

latexboxplot.write("\\end{axis}\n\\end{tikzpicture}")
latexboxplot.close()