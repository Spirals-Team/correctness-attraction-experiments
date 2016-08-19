
explorations=( ("IntegerAddOne", "1") , ("BooleanNegation","NEGB") )
subjects=("quicksort", "zip", "sudoku", "md5", "rsa", "rc4", "canny", "lcs", "laguerre", "linreg" )

nbPerturbationExecutions = 0
nbCorrectOutput = 0
nbExecReference = 0
for exploration,perturbation in explorations:
    for subject in subjects:
        lines = [line.rstrip('\n') for line in open("results/" + subject + "/"+exploration+"_CallExplorer_search_space_size_"+perturbation+".txt")]
        nbExecReference += int(' '.join(lines[1].split()).split(" ")[-1]) * int(' '.join(lines[2].split()).split(" ")[-1])
        nbPerturbationExecutions += int(' '.join(lines[3].split()).split(" ")[-1])
        nbCorrectOutput += int(' '.join(lines[4].split()).split(" ")[-1])

print("nbExecRef " + str(nbExecReference))
print("nbPerturbationExecution " + str(nbPerturbationExecutions))
print("total (p + ref) " + str( nbExecReference + nbPerturbationExecutions ))
print("nbCorrectOutput " + str(nbCorrectOutput))
print("Percentage " + str(float(nbCorrectOutput) / float(nbPerturbationExecutions) * 100.0))