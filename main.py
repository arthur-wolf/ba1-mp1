def main():

    # Opening and reading the file
    with open('output.txt') as f:
        lines = f.readlines()

        # Defining variables
        ok = 0
        false_pos = 0
        false_neg = 0

        # Getting data
        for line in lines:
            splitted = line.split()
            print(splitted[0] + " : " + splitted[3] + " ~ " + splitted[5]
                  + " / Expected : " + splitted[8] + " - Computed : " + splitted[11])

            # Processing data
            if splitted[8] == splitted[11]:
                ok += 1
            elif (splitted[8] == "false" or splitted[8] == "false.") and \
                    (splitted[11] == "true" or splitted[11] == "true."):  # false positives
                false_pos += 1
            elif (splitted[8] == "true" or splitted[8] == "true.") and \
                    (splitted[11] == "false" or splitted[11] == "false."):  # false negatives
                false_neg += 1

        # Computing results
        length = len(lines)
        print()
        print("--------------------------------------------------")
        print()
        print(f"{length} fingerprints have been compared :")
        ratio = (ok / length) * 100
        fp_amount = (false_pos / length) * 100
        fn_amount = (false_neg / length) * 100
        print(f"Ratio is {ratio} %")
        print(f"We have {fp_amount} % false positives")
        print(f"We have {fn_amount} % false negatives")


if __name__ == '__main__':
    main()
