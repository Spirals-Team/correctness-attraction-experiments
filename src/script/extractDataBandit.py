lines = [line.rstrip('\n') for line in open("results/bitcoin/BooleanNegation_Bandit_data_graph_analysis.txt")]

perturb=[]
accCall=0
accPerturb=0
accSuccess=0
maxPerturb=-1
accSuccess_perc=0.0
for i in range(7, len(lines)):
   accCall += int(' '.join(lines[i].split()).split(" ")[5])
   currentPerturb = int(' '.join(lines[i].split()).split(" ")[1])
   accPerturb += currentPerturb
   perturb.append(currentPerturb)
   accSuccess += int(' '.join(lines[i].split()).split(" ")[2])
   if currentPerturb > maxPerturb:
       maxPerturb = currentPerturb
   accSuccess_perc += float( (' '.join(lines[i].split()).split(" ")[-1]).replace(',','.'))
perturb.sort()
print("total p " +  str(accPerturb))
print("median p " + str(perturb[len(perturb) / 2]))
print("max p "  + str(maxPerturb))
print("number of loc " + str(len(lines) - 7))
print("avg succ " + str(accSuccess_perc / (len (lines) - 7)))