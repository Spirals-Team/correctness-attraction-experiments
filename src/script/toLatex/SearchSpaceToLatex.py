
#subjects=("quicksort", "zip", "sudoku", "md5", "TEA", "rc4", "laguerre", "rsa", "classifier", "torrent", "bitcoin") #11 project, probably remove TEA
subjects=("quicksort", "zip", "sudoku", "md5", "rc4", "laguerre", "rsa", "classifier", "torrent", "bitcoin") #11 project, probably remove TEA

def barchart(success):
    return '-' * int((float(success.replace(",", "."))) / 10)

out = ""

for subject in subjects:
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/IntegerAddOne_CallExplorer_search_space_size_1.txt")]
    npp = ' '.join(lines[2].split()).split(" ")[-1]
    executions = ' '.join(lines[3].split()).split(" ")[-1]
    success =  ' '.join(lines[5].split()).split(" ")[-1]

    out += subject + "&" + npp + "&" + executions + "&"

    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/IntegerAddOne_CallExplorer_super_anti_fragile.txt")]

    nsuperantifragile = "-"
    nantifragile = "-"
    if len(lines)>1:
        nsuperantifragile = len(' '.join(lines[1].split()).split(" "))
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/IntegerAddOne_CallExplorer_anti_fragile.txt")]
    if len(lines)>1:
        nantifragile = len(' '.join(lines[1].split()).split(" "))


    out += str(nsuperantifragile) + "&" + str(nantifragile) + "&" + barchart(success) + " " + success

    out += "\\\\\n"

print(out)
