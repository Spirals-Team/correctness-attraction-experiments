
#subjects=("quicksort", "zip", "sudoku", "md5", "TEA", "rc4", "laguerre", "rsa", "classifier", "torrent", "bitcoin") #11 project, probably remove TEA
subjects=("quicksort", "zip", "sudoku", "md5", "rc4", "rsa", "classifier", "torrent") #11 project, probably remove TEA

def barchart(success):
    return '-' * int((float(success.replace(",", "."))) / 10)

str = ""

for subject in subjects:
    lines = [line.rstrip('\n') for line in
             open("results/" + subject + "/IntegerAddOne_CallExplorer_search_space_size_1.txt")]
    npp = ' '.join(lines[2].split()).split(" ")[-1]
    executions = ' '.join(lines[3].split()).split(" ")[-1]
    success =  ' '.join(lines[5].split()).split(" ")[-1]
    str += subject + "&" + npp + "&" + executions + "&" + barchart(success) + " "+ success + "\\\\\n"

print(str)
