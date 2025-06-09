import React from 'react'
import { Datagrid, List, TextField } from 'react-admin'

const CategoryList = () => {
  return (
    <List>
      <Datagrid>
        <TextField source="id" />
        <TextField source="name" />
        <TextField source="code" />
        <TextField source="description" />
      </Datagrid>
    </List>
  )
}

export default CategoryList