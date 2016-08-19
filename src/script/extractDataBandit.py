
#subject="bitcoin"
subject="torrent"

perturb=[]
accCall=0
accPerturb=0
accSuccess=0
accSuccess_all=0
maxPerturb=-1
accExec=0

lines = [line.rstrip('\n') for line in open("results/"+subject+"/IntegerAddOne_Bandit_data_graph_analysis.txt")]
for i in range(7, len(lines)):
   accCall += int(' '.join(lines[i].split()).split(" ")[5])
   currentPerturb = int(' '.join(lines[i].split()).split(" ")[1])
   accPerturb += currentPerturb
   accExec += currentPerturb
   perturb.append(currentPerturb)
   accSuccess += int(' '.join(lines[i].split()).split(" ")[2])
   if currentPerturb > maxPerturb:
       maxPerturb = currentPerturb
   #accSuccess_perc += float( (' '.join(lines[i].split()).split(" ")[-1]).replace(',','.'))

#Nb pp int
print("#pp int : " + str(len(lines) - 7))
print("correctness ratio int " + str(float(float(accSuccess) / float(accExec))*100.0))

accSuccess_all=accSuccess

accSuccess=0
accExec=0

lines = [line.rstrip('\n') for line in open("results/"+subject+"/BooleanNegation_Bandit_data_graph_analysis.txt")]
for i in range(7, len(lines)):
   accCall += int(' '.join(lines[i].split()).split(" ")[5])
   currentPerturb = int(' '.join(lines[i].split()).split(" ")[1])
   accPerturb += currentPerturb
   accExec += currentPerturb
   perturb.append(currentPerturb)
   accSuccess += int(' '.join(lines[i].split()).split(" ")[2])
   if currentPerturb > maxPerturb:
      maxPerturb = currentPerturb
   #accSuccess_perc += float( (' '.join(lines[i].split()).split(" ")[-1]).replace(',','.'))

accSuccess_all=accSuccess+accSuccess_all

#Nb pp bool
print("#pp bool : " + str(len(lines) - 7))
print("correctness ratio bool " + str(float(float(accSuccess) / float(accExec))*100.0))

perturb.sort()
print("median p " + str(perturb[len(perturb) / 2]))
print("max p "  + str(maxPerturb))
print("# exec p " +  str(accPerturb))
print("# correct output : " + str(accSuccess_all))