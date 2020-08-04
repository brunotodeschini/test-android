# Teste desenvolvedor android IBM/Sicredi

Este projeto foi pensado de maneira a utilizar a arquitetura MVVM. E utilizou algumas tecnologias disponíveis no Android Jetpack, como por exemplo:

 - Coroutines
 - LiveData
 - DataBinding

Para conexões HTTP, foi utilizado o Retrofit com o Gson (para converter o json em objeto). Para o download de imagens foi utilizado o Glide, ele é simples de ser utilizado e seu pacote de dependências é pequeno.
Para o layout foi utilizado Constraint layout que permite a construção de layouts mais complexos com certa facilidade, recyclerview para a listagem de eventos, já que com a mesma podemos reutilizar a view, evitando uma sobre carga no app e lentidão.
 
