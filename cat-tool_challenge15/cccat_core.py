import sys
class Cccat:
    def __init__(self,  filenames,arguments, isPiped = False) -> None:
        numberedLines = arguments.get("-n")==1 or arguments.get("-b")==1
        skipBlank = arguments.get("-b") == 1
        print(numberedLines)
        if(isPiped):
            line = sys.stdin.readline()
            i=1
            while line:
                if skipBlank and line.strip() == '':
                    line = sys.stdin.readline()
                    continue
                if numberedLines:
                    line = f"{i}) {line}"
                print(line, end='')
                line = sys.stdin.readline()
                i+=1
        else:
            for filename in filenames:
                print(f"content of {filename}")
                with open(filename,'r') as reader:
                    i = 1
                    for line in reader:
                        if skipBlank and line.strip() == '':
                            continue
                        if numberedLines:
                            line = f"{i}) {line}"
                        i+=1
                        print(line)

                    