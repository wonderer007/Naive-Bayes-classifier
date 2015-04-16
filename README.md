# Naive-Bayes-classifier
Naive Bayes classifier is classification algorithm. It uses Naive based Bernoulli and Multinomial equation to classify documents(Text) as ham or spam.  Currently algorithm is only classifying in two categories but it can be modified to  classify a text in N categories.

<b>Project Structure</b><br/>
<b>/jars </b> executable jars if you don't want to run source code  <br/>
<b>/src/main </b> source code of Naive-Bayes-classifier  <br/>
<b>/src/corpus </b>  contains all data files trainining files and testing files, list of stop words file <br/>

you can run project by either importing it in eclipse or using cmd/termianl in java based environment <br/>

<h1> Using Eclipse </h1>
<ul>
  <li> Import Project in eclipse ( <i>using Existing Project into Workspace option</i> )</li>
  <li> run Train.java (main class) to train you corpus using <i>corpus_root_path</i> as run time argument </li>
  <li> After running Train.java check out <i>Bernouli.out</i> and <i>Multinomail.out</i> files at corpus_root_path. these are model build by bernouli and multinomial calculations</li>
  <li>Now run Test.java (main class ) to test any of your ham, spam or any type of file</li>

</ul>