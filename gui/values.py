import math

methods = ["Метод дихотомии",
           "Метод золотого сечения",
           "Метод парабол",
           "Метод Фибоначчи",
           "Метод Брента"]


def function(x):
    return pow(x, 4) - 1.5 * math.atan(x)


jar_path = "Demo.jar"

input_mosk = """0.6426359073705743
-0.6862181514405017
-0.3333333333333333 0.494971510607309 0.3333333333333333 -0.4702801525826176
-0.3333333333333333 0.494971510607309 0.3333333333333333 -0.4702801525826176
0.11111111111111116 -0.16583341597056767 0.5555555555555556 -0.665387887666085
0.40740740740740744 -0.5527639520850723 0.7037037037037037 -0.6745890701692703
0.6049382716049383 -0.6821443780490796 0.8024691358024691 -0.5996868013359271
0.5390946502057613 -0.6571863165501128 0.6707818930041153 -0.6838157808163278
0.6268861454046639 -0.6854935582196361 0.7146776406035664 -0.669883438512339
0.5976223136716964 -0.6804461930367289 0.6561499771376315 -0.6856711697372828
0.6366407559823197 -0.6861122818287224 0.675659198292943 -0.6828973196821837
0.6236346085454453 -0.6851664237438726 0.6496469034191942 -0.6860717476338091
0.6409761384612778 -0.6862100067134362 0.6583176683771104 -0.6854802607742483
0.6351956284893336 -0.6860552920602874 0.6467566484332219 -0.6861677001312732
0.6429029751185925 -0.6862179402255164 0.6506103217478515 -0.6860285911371684
0.6403338595755064 -0.686202492183941 0.6454720906616789 -0.6861942780593348
0.6386211158801154 -0.6861705924824251 0.6420466032708971 -0.6862171237535981
0.6409047741406365 -0.6862092918078091 0.6431884324011578 -0.6862172471709986
0.6424272129809839 -0.686218022510305 0.6439496518213313 -0.6862130357835423
0.6419197333675348 -0.6862166337927912 0.642934692594433 -0.6862178870699226
0.6425963728521336 -0.6862181468122682 0.6432730123367325 -0.6862169490429222
0.6423708263572674 -0.6862179434391664 0.642821919347 -0.6862180489866474
0.6426715550170891 -0.6862181476788476 0.6429722836769108 -0.686217816350987
0.6425713121304819 -0.6862181390860849 0.6427717979036964 -0.6862180967641069
0.6425044835394104 -0.6862181003053981 0.6426381407215535 -0.6862181514257835
0.6425935883275057 -0.686218146137429 0.642682693115601 -0.6862181449605756
0.6425638867314739 -0.6862181360827645 0.6426232899235375 -0.6862181509688837
0.6426034888595162 -0.68621814832829 0.6426430909875587 -0.6862181512878744
0.6426298902782113 -0.6862181513331758 0.6426562916969061 -0.6862181502106973
0.6426210898053129 -0.6862181507901294 0.6426386907511095 -0.6862181514176259
0.6426328237691773 -0.6862181514122814 0.6426445577330416 -0.6862181512191455
0.642628912447889 -0.6862181512954837 0.6426367350904654 -0.6862181514384915
0.6426341275429399 -0.6862181514310834 0.6426393426379908 -0.686218151405638
0.642632389177923 -0.6862181514037772 0.6426358659079568 -0.6862181514404957
0.6426347069979457 -0.6862181514362091 0.6426370248179682 -0.6862181514368294
0.6426362522112941 -0.6862181514401573 0.6426377974246424 -0.686218151429967
0.642635737140178 -0.6862181514404121 0.6426367672824101 -0.6862181514383314
0.6426353937594338 -0.6862181514397092 0.6426360805209219 -0.6862181514404166
0.6426358516004258 -0.686218151440491 0.642636309441418 -0.686218151440032
0.6426356989867619 -0.6862181514403685 0.64263600421409 -0.6862181514404762
0.6426359024716473 -0.6862181514405015 0.6426361059565326 -0.6862181514403893
0.6426358346433522 -0.6862181514404844 0.6426359702999423 -0.6862181514404914
0.642635925081079 -0.686218151440501 0.6426360155188059 -0.6862181514404695
0.64263589493517 -0.6862181514405009 0.642635955226988 -0.6862181514404959
0.6426358748378974 -0.6862181514404977 0.6426359150324427 -0.6862181514405017
0.642635901634261 -0.6862181514405015 0.6426359284306246 -0.6862181514405007
0.6426358927021398 -0.6862181514405006 0.6426359105663821 -0.6862181514405016
0.6426359046116347 -0.6862181514405016 0.6426359165211296 -0.6862181514405016
0.6426359006418032 -0.6862181514405015 0.6426359085814664 -0.6862181514405016
0.6426359059349119 -0.6862181514405016 0.6426359112280208 -0.6862181514405017
0.6426359094636511 -0.6862181514405017 0.6426359129923904 -0.6862181514405016
0.6426359082874047 -0.6862181514405018 0.6426359106398976 -0.6862181514405017
0.6426359075032404 -0.6862181514405017 0.642635909071569 -0.6862181514405016
0.6426359069804642 -0.6862181514405016 0.6426359080260166 -0.6862181514405018
0.6426359076774992 -0.6862181514405016 0.6426359083745341 -0.6862181514405016
0.6426359074451542 -0.6862181514405017 0.6426359079098443 -0.6862181514405017
0.6426359072902575 -0.6862181514405017 0.6426359076000508 -0.6862181514405017
0.6426359071869931 -0.6862181514405016 0.6426359073935219 -0.6862181514405018
0.642635907324679 -0.6862181514405018 0.6426359074623649 -0.6862181514405017
0.6426359072787837 -0.6862181514405016 0.6426359073705743 -0.6862181514405017"""
