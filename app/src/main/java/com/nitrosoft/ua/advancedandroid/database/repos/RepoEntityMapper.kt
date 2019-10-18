package com.nitrosoft.ua.advancedandroid.database.repos

import com.nitrosoft.ua.advancedandroid.database.Mapper
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoEntityMapper @Inject constructor() : Mapper<RepoEntity, Repo> {

    override fun mapFromEntity(entity: RepoEntity): Repo {
        return Repo(
                entity.id,
                entity.name,
                entity.description,
                User(entity.userId, entity.userLogin),
                entity.starGazersCount,
                entity.forksCount,
                entity.contributorsUrl,
                entity.createdDate,
                entity.updatedDate
        )
    }

    override fun mapToEntity(model: Repo): RepoEntity {
        return RepoEntity(
                model.id,
                model.name,
                model.description,
                model.user.id,
                model.user.login,
                model.starGazersCount,
                model.forksCount,
                model.contributorsUrl,
                model.createdDate,
                model.updatedDate
        )
    }
}