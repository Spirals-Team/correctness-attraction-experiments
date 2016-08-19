from matplotlib import pyplot as plt

lines = [line.rstrip('\n') for line in open("results/quicksort/outputExec.txt")]
nbSuccess, nbFailure = ' '.join(lines[0].split()).split(" ")

fig = plt.figure()
ax = fig.add_axes((.1,.4,.8,.5))

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

plt.xlabel("time (in recursive call)")
plt.ylabel("Number of unsorted pairs")
plt.title("Executions of Quicksort")

fig.savefig("results/quicksort/img/exec_path.pdf", bbox_inches='tight')
ax.set_xscale('symlog')
ax.set_yscale('symlog')
fig.savefig("results/quicksort/img/exec_path_log.pdf", bbox_inches='tight')
plt.close(fig)


