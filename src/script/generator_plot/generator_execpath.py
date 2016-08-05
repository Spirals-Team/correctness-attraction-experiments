from matplotlib import pyplot as plt

lines = [line.rstrip('\n') for line in open("results/quicksort/outputExec.txt")]
nbSuccess, nbFailure = ' '.join(lines[0].split()).split(" ")

fig = plt.figure()

for i in range(0, int(nbSuccess)):
    print(str(i) + " / " + nbSuccess)
    array = [int(x) for x in ' '.join(lines[2+i].split()).split(" ")]
    plt.plot(range(0,len(array)), array, color=(0.4,1,0.4), alpha=0.55,  linewidth=0.6)

for i in range(0, int(nbFailure)):
    print(str(i) + " / " + nbFailure)
    array = [int(x) for x in ' '.join(lines[2+int(nbSuccess)+i].split()).split(" ")]
    plt.plot(range(0,len(array)), array, color=(1,0.6,0.6), alpha=0.55,  linewidth=0.6)

path_reference = [int(x) for x in ' '.join(lines[1].split()).split(" ")]
plt.plot(range(0,len(path_reference)), path_reference, 'b-', label="unperturbed", linewidth=1.5)

fig.savefig("results/quicksort/img/exec_path_1.pdf")
plt.close(fig)

