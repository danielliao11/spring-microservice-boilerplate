import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import { TableBody, TableRow, TableCell } from '@material-ui/core';

// core
import TableCard from '../components/commons/TableCard';

// static
import resourcePageStyle from '../styles/jss/containers/resourcePageStyle';

const ResourceTableBody = ({ ...props }) => {
  const { resource } = props;
  return (
    <TableBody>
      {resource.content.map(row => (
        <TableRow key={row.id}>
          <TableCell>{row.name}</TableCell>
          <TableCell>{row.createdAt}</TableCell>
        </TableRow>
      ))}
    </TableBody>
  );
};

@inject('resource')
@observer
class ResourcePage extends React.Component {
  componentDidMount() {
    const { resource } = this.props;
    resource.fetchPage();
  }

  render() {
    const { classes, resource } = this.props;
    return (
      <TableCard
        cardTitle="Resource"
        cardDescription="Access resource"
        tableBody={<ResourceTableBody resource={resource} />}
        tablePaginationStore={resource}
      />
    );
  }
}

ResourcePage.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

ResourcePage.wrappedComponent.propTypes = {
  resource: PropTypes.observableObject.isRequired,
};

ResourceTableBody.propTypes = {
  resource: PropTypes.observableObject.isRequired,
};

export default withStyles(resourcePageStyle)(ResourcePage);
