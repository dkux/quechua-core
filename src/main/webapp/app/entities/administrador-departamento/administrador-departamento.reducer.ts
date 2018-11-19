import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdministradorDepartamento, defaultValue } from 'app/shared/model/administrador-departamento.model';

export const ACTION_TYPES = {
  FETCH_ADMINISTRADORDEPARTAMENTO_LIST: 'administradorDepartamento/FETCH_ADMINISTRADORDEPARTAMENTO_LIST',
  FETCH_ADMINISTRADORDEPARTAMENTO: 'administradorDepartamento/FETCH_ADMINISTRADORDEPARTAMENTO',
  CREATE_ADMINISTRADORDEPARTAMENTO: 'administradorDepartamento/CREATE_ADMINISTRADORDEPARTAMENTO',
  UPDATE_ADMINISTRADORDEPARTAMENTO: 'administradorDepartamento/UPDATE_ADMINISTRADORDEPARTAMENTO',
  DELETE_ADMINISTRADORDEPARTAMENTO: 'administradorDepartamento/DELETE_ADMINISTRADORDEPARTAMENTO',
  RESET: 'administradorDepartamento/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdministradorDepartamento>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AdministradorDepartamentoState = Readonly<typeof initialState>;

// Reducer

export default (state: AdministradorDepartamentoState = initialState, action): AdministradorDepartamentoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ADMINISTRADORDEPARTAMENTO):
    case REQUEST(ACTION_TYPES.UPDATE_ADMINISTRADORDEPARTAMENTO):
    case REQUEST(ACTION_TYPES.DELETE_ADMINISTRADORDEPARTAMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO):
    case FAILURE(ACTION_TYPES.CREATE_ADMINISTRADORDEPARTAMENTO):
    case FAILURE(ACTION_TYPES.UPDATE_ADMINISTRADORDEPARTAMENTO):
    case FAILURE(ACTION_TYPES.DELETE_ADMINISTRADORDEPARTAMENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADMINISTRADORDEPARTAMENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_ADMINISTRADORDEPARTAMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADMINISTRADORDEPARTAMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/administrador-departamentos';

// Actions

export const getEntities: ICrudGetAllAction<IAdministradorDepartamento> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO_LIST,
  payload: axios.get<IAdministradorDepartamento>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAdministradorDepartamento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADMINISTRADORDEPARTAMENTO,
    payload: axios.get<IAdministradorDepartamento>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAdministradorDepartamento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADMINISTRADORDEPARTAMENTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdministradorDepartamento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADMINISTRADORDEPARTAMENTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdministradorDepartamento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADMINISTRADORDEPARTAMENTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
