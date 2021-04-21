package com.alan.intermediatest.utils

class Constants {
    companion object {
        const val TS = 1
        const val API_KEY = "f0eb41038719bca9568027285ddb6d4e"
        const val HASH =
            "f0ddb2650c2484deea01a6465b39460a" //Combination between ts + private key + public key
        const val SERVER_URL = "http://gateway.marvel.com/v1/public/"

        //Images sizes
        const val IMAGE_SMALL = "portrait_small"
        const val IMAGE_MEDIUM = "portrait_medium"
        const val IMAGE_XL = "portrait_xlarge"
        const val IMAGE_FANTASTIC = "portrait_fantastic"
        const val IMAGE_UNCANNY = "portrait_uncanny"
        const val IMAGE_INCREDIBLE = "portrait_incredible"

        //Pagination
        const val CHARACTERS_LIMIT = 15
        const val EVENTS_LIMIT = 25
    }
}