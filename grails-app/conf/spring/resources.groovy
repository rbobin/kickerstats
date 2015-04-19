import kickerstats.Converters

// Place your Spring DSL code here
beans = {
    converterBean(Converters){bean ->
        bean.autowire = 'byName'
    }
}
