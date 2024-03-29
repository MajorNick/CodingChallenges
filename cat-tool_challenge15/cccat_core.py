import sys
class Cccat:
    def __init__(self,  filenames,arguments, isPiped = False) -> None:

        if(isPiped):
            line = sys.stdin.readline()
            i=0
            while line:
                if arguments.get("-b") == 1 and line != '\n':

                    i+=1
                    line = f"{i}) {line}"
                print(line, end='')
                line = sys.stdin.readline()
        else:
            for filename in filenames:
                print(f"content of {filename}")
                with open(filename,'r') as reader:
                    i = 0
                    for line in reader:
                        if arguments.get("-b") == 1 and line != '\n':
                            i+=1
                            line = f"{i}) {line}"
                        print(line)