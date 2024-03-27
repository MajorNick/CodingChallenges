class Cccat:
    def __init__(self,  filename,arguments) -> None:
        print(filename)
        print(arguments)
        with open(filename,'r') as reader:
            i = 0
            for line in reader:
                
                if arguments.get("-b") == 1 and line != '\n':
                    i+=1
                    line = f"{i}) {line}"
                print(line)